package com.challenge.toll.refactored;

import com.challenge.toll.refactored.model.Vehicle;
import com.google.common.base.Preconditions;

import java.time.*;
import java.util.Arrays;
import java.util.List;

public final class RefactoredTollCalculator {

    private static final int DAY_MAXIMUM_TOTAL_FEE = 60;

    private RefactoredTollCalculator() {}

    /**
     * Calculate the total toll fee for one day
     * @param vehicle - the vehicle
     * @param dates   - date and time of all passes on one day
     * @return - the total toll fee for that day
     */
    public static int calculate(Vehicle vehicle, LocalDateTime...dates) {
        // Previous code didn't mind null vehicle, we could create a UNKNOWN vehicle eventually
        Preconditions.checkArgument(vehicle != null, "vehicle cannot be null");
        Preconditions.checkArgument(dates != null, "dates cannot be null - at least one needs to be specified");
        Preconditions.checkArgument(isOnSameDay(dates), "dates must be for the same day");

        return calculateAll(vehicle, dates);
    }

    private static int calculateAll(Vehicle vehicle, LocalDateTime...dates) {
        //TODO clarify whether dates come sorted - don't want to change behavior but I would sort them
        LocalDateTime intervalStart = dates[0];
        final int feeFirstTrip = calculateOne(vehicle, intervalStart);

        // Always add the initial fee and iterate over remainder
        int total = feeFirstTrip;
        List<LocalDateTime> remaining = Arrays.asList(dates).subList(1, dates.length);

        for (LocalDateTime date : remaining) {
            int feeThisTrip = calculateOne(vehicle, date);
            long minutesSinceFirstTrip = Duration.between(intervalStart, date).toMinutes();

            if (minutesSinceFirstTrip > 60) {
                // Full price mode
                total = total + feeThisTrip;
            } else if (feeFirstTrip < feeThisTrip) {
                // if 1st trip was <= 1 hour ago and this trip is more expensive, add the difference
                total = total + Math.abs(feeThisTrip - feeFirstTrip);
            }
        }
        return total > DAY_MAXIMUM_TOTAL_FEE ? DAY_MAXIMUM_TOTAL_FEE : total;
    }

    private static int calculateOne(Vehicle vehicle, LocalDateTime date) {
        if (TollFreeVehicleChecker.isTollFree(vehicle) || TollFreeDateChecker.isTollFree(date.toLocalDate())) {
            return 0;
        }
        return TimeRangeFeeCalculator.calculate(date.toLocalTime());
    }

    static boolean isOnSameDay(LocalDateTime...dates) {
        if (dates.length == 1) return true;

        LocalDate day = dates[0].toLocalDate();
        for (LocalDateTime date : dates) {
            Preconditions.checkArgument(date != null, "date cannot be null");
            if (!day.isEqual(date.toLocalDate())) return false;
        }
        return true;
    }

    // More defensive version since requirements are not easily understandable and there's no test

//    private static int calculateAll(Vehicle vehicle, LocalDateTime...dates) {
//        LocalDateTime intervalStart = dates[0];
//        int feeFirstTrip = calculateOne(vehicle, intervalStart);
//
//        int total = 0;
//        for (LocalDateTime date : dates) {
//            int feeThisTrip = calculateOne(vehicle, date);
//            long minutesSinceFirstTrip = Duration.between(intervalStart, date).toMinutes();
//
//            // Seems like this means the fee is the delta between current and 1st trip only but not 100% sure so keeping logic unchanged
//            if (minutesSinceFirstTrip <= 60) {
//                if (total > 0) {
//                    total = total - feeFirstTrip;
//                }
//                total = total + Math.max(feeFirstTrip, feeThisTrip);
//            } else {
//                total = total + feeThisTrip;
//            }
//        }
//        return total > DAY_MAXIMUM_TOTAL_FEE ? DAY_MAXIMUM_TOTAL_FEE : total;
//    }

}
