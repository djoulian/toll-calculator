package com.challenge.toll.refactored;

import com.google.common.collect.Range;

import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;

final class TimeRangeFeeCalculator {

    // Ideally make immutable and pull rules from file or so, e.g. json - this is much likely config and could change
    private static final Map<Range<LocalTime>, Integer> RANGES = new LinkedHashMap<>();
    static {
        RANGES.put(timeRange("06:00", "06:30"), 8);
        RANGES.put(timeRange("06:30", "07:00"), 13);
        RANGES.put(timeRange("07:00", "08:00"), 18);
        RANGES.put(timeRange("08:00", "08:30"), 13);
        RANGES.put(timeRange("08:30", "09:00"), 8);
        RANGES.put(timeRange("09:30", "10:00"), 8);
        RANGES.put(timeRange("10:30", "11:00"), 8);
        RANGES.put(timeRange("11:30", "12:00"), 8);
        RANGES.put(timeRange("12:30", "13:00"), 8);
        RANGES.put(timeRange("13:30", "14:00"), 8);
        RANGES.put(timeRange("14:30", "15:00"), 8);
        RANGES.put(timeRange("15:00", "15:30"), 13);
        RANGES.put(timeRange("15:30", "17:00"), 18);
        RANGES.put(timeRange("17:00", "18:00"), 13);
        RANGES.put(timeRange("18:00", "18:30"), 8);
    }

    private static final Range<LocalTime> timeRange(String lower, String upper) {
        // Use closedOpen to handle in/exclusions this way: "lower <= VALUE < upper"
        return Range.closedOpen(LocalTime.parse(lower), LocalTime.parse(upper));
    }

    private TimeRangeFeeCalculator() {}

    public static int calculate(LocalTime localTime) {
        for (Map.Entry<Range<LocalTime>, Integer> range : RANGES.entrySet()) {
            if (range.getKey().contains(localTime)) {
                return range.getValue();
            }
        }
        // Business rule is to return 0 if range/amount was not specified
        return 0;
    }

}
