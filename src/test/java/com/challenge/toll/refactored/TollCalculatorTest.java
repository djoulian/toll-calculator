package com.challenge.toll.refactored;

import com.google.common.collect.Lists;
import com.challenge.toll.refactored.model.Car;
import com.challenge.toll.refactored.model.UseCase;

import static org.junit.Assert.*;

import org.junit.Test;

import com.challenge.toll.existing.TollCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TollCalculatorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TollCalculatorTest.class);

    @Test
    public void testArgumentDatesOnSameDaySuccess() {
        assertTrue(RefactoredTollCalculator.isOnSameDay(
                toLocalDateTime("2016-10-17T00:00:00"),
                toLocalDateTime("2016-10-17T12:00:00"),
                toLocalDateTime("2016-10-17T23:59:59")
        ));
    }

    @Test
    public void testArgumentOneDateSuccess() {
        assertTrue(RefactoredTollCalculator.isOnSameDay(
            toLocalDateTime("2016-10-17T12:30:00")
        ));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArgumentDatesOnSameDayFailure() {
        RefactoredTollCalculator.calculate(
                new Car(),
                toLocalDateTime("2016-10-17T23:59:01"),
                toLocalDateTime("2016-10-18T00:00:00")
        );
        fail("should not have reached this point");
    }

    @Test
    public void testSimpleIntervals() {
        Car car = new Car();
        com.challenge.toll.existing.Car oldCar = new com.challenge.toll.existing.Car();

        // Test simple intervals
        List<UseCase> simpleCases = Lists.newArrayList(
                new UseCase.Builder().vehicle(car, oldCar).date("2016-10-17T05:59:59").expect(0).build(),
                new UseCase.Builder().vehicle(car, oldCar).date("2016-10-17T06:00:00").expect(8).build(),
                new UseCase.Builder().vehicle(car, oldCar).date("2016-10-17T06:00:01").expect(8).build(),
                new UseCase.Builder().vehicle(car, oldCar).date("2016-10-17T06:29:59").expect(8).build(),
                new UseCase.Builder().vehicle(car, oldCar).date("2016-10-17T06:30:31").expect(13).build(),
                new UseCase.Builder().vehicle(car, oldCar).date("2016-10-17T06:58:59").expect(13).build(),
                new UseCase.Builder().vehicle(car, oldCar).date("2016-10-17T06:59:00").expect(13).build(),
                new UseCase.Builder().vehicle(car, oldCar).date("2016-10-17T06:59:59").expect(13).build(),
                new UseCase.Builder().vehicle(car, oldCar).date("2016-10-17T07:00:00").expect(18).build(),
                new UseCase.Builder().vehicle(car, oldCar).date("2016-10-17T07:00:01").expect(18).build(),
                new UseCase.Builder().vehicle(car, oldCar).date("2016-10-17T08:28:59").expect(13).build(),
                new UseCase.Builder().vehicle(car, oldCar).date("2016-10-17T08:29:00").expect(13).build(),
                new UseCase.Builder().vehicle(car, oldCar).date("2016-10-17T08:29:01").expect(13).build(),
                new UseCase.Builder().vehicle(car, oldCar).date("2016-10-17T08:29:59").expect(13).build(),
                new UseCase.Builder().vehicle(car, oldCar).date("2016-10-17T08:30:00").expect(8).build(),
                new UseCase.Builder().vehicle(car, oldCar).date("2016-10-17T08:30:01").expect(8).build(),
                new UseCase.Builder().vehicle(car, oldCar).date("2016-10-17T12:15:30").expect(0).build(),
                new UseCase.Builder().vehicle(car, oldCar).date("2016-10-17T12:31:00").expect(8).build()
        );

        for (UseCase useCase : simpleCases) {
            // Old
            TollCalculator oldCalculator = new TollCalculator();
            int feeOld = oldCalculator.getTollFee(useCase.getOldVehicle(), toDates(useCase.getDates()));

            // Refactored
            int feeNew = RefactoredTollCalculator.calculate(useCase.getVehicle(), toLocalDateTimes(useCase.getDates()));

            LOGGER.info(String.format("%s, feeOld=%d, feeNew=%d", useCase, feeOld, feeNew));

            assertEquals(useCase.getExpected(), feeOld);
            assertEquals(useCase.getExpected(), feeNew);
        }
    }

    // BDD approach would be better... Did not put much effort into this
    // should also split in different methods and cleanup
    @Test
    public void testMultipleTrips() {

        testMulipleDatesEqualsBetweenBothCalculators(
                8,
                new String[]{
                        "2016-10-17T00:30:00",
                        "2016-10-17T06:00:00"
        });

        testMulipleDatesEqualsBetweenBothCalculators(
                13,
                new String[]{
                        "2016-10-17T17:50:00",
                        "2016-10-17T18:10:00",
                        "2016-10-17T18:10:01",
                        "2016-10-17T18:10:02"
        });

        testMulipleDatesEqualsBetweenBothCalculators(
                13,
                new String[]{
                        "2016-10-17T17:50:00",
                        "2016-10-17T18:10:00"
        });

        testMulipleDatesEqualsBetweenBothCalculators(
                21,
                new String[]{
                        "2016-10-17T09:30:00",
                        "2016-10-17T15:00:00"
        });

        testMulipleDatesEqualsBetweenBothCalculators(
                23,
                new String[]{
                        "2016-10-17T06:00:00", // 8
                        "2016-10-17T06:30:00", // 13
                        "2016-10-17T07:00:00"  // 18
        });

        testMulipleDatesEqualsBetweenBothCalculators(
                18,
                new String[]{
                        "2016-10-17T07:00:00",
                        "2016-10-17T06:30:00",
                        "2016-10-17T06:00:00"
        });

        testMulipleDatesEqualsBetweenBothCalculators(
                39,
                new String[]{
                        "2016-10-17T05:59:00",
                        "2016-10-17T06:00:00",
                        "2016-10-17T06:30:00",
                        "2016-10-17T07:00:00"
        });

        testMulipleDatesEqualsBetweenBothCalculators(
                59,
                new String[]{
                        "2016-10-17T06:00:00",
                        "2016-10-17T06:30:00",
                        "2016-10-17T07:00:00",
                        "2016-10-17T07:01:00",
                        "2016-10-17T07:01:00"
        });
    }

    private void testMulipleDatesEqualsBetweenBothCalculators(int expected, String...dates) {
        int feeOld = new TollCalculator().getTollFee(new com.challenge.toll.existing.Car(), toDates(dates));
        int feeNew = RefactoredTollCalculator.calculate(new Car(), toLocalDateTimes(dates));
        LOGGER.info("newFee={}, newFee={}, dates={}", feeOld, feeNew, dates);
        assertEquals("test is wrong/invalid, old calculator is right", feeOld, expected);
        assertEquals("new calculator is wrong, not same value as old one", feeOld, feeNew);
    }

    private LocalDateTime toLocalDateTime(String date) {
        return LocalDateTime.parse(date);
    }

    private LocalDateTime[] toLocalDateTimes(String... dates) {
        List<LocalDateTime> results = new ArrayList<>();
        for (String date : dates) {
            results.add(LocalDateTime.parse(date));
        }
        return results.toArray(new LocalDateTime[results.size()]);
    }

    private Date[] toDates(String... dates) {
        List<Date> results = new ArrayList<>();
        for (String date : dates) {
            LocalDateTime dateTime = LocalDateTime.parse(date);
            results.add(Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()));
        }
        return results.toArray(new Date[results.size()]);
    }

}
