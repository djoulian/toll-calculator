package com.challenge.toll.refactored;

import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;

public class TollFreeDateCheckerTest {

    @Test
    public void testWeekends() {
        // Weekends
        assertTrue(TollFreeDateChecker.isTollFree(toDate("2016-10-15")));
        assertTrue(TollFreeDateChecker.isTollFree(toDate("2016-10-16")));
        assertTrue(TollFreeDateChecker.isTollFree(toDate("2016-10-22")));
        assertTrue(TollFreeDateChecker.isTollFree(toDate("2016-10-23")));

        // Non weekends
        assertFalse(TollFreeDateChecker.isTollFree(toDate("2016-10-17")));
        assertFalse(TollFreeDateChecker.isTollFree(toDate("2016-10-18")));
        assertFalse(TollFreeDateChecker.isTollFree(toDate("2016-10-19")));
        assertFalse(TollFreeDateChecker.isTollFree(toDate("2016-10-20")));
        assertFalse(TollFreeDateChecker.isTollFree(toDate("2016-10-21")));
    }

    @Test
    public void testYearMonths() {
        // Different dates in 2013/07
        assertTrue(TollFreeDateChecker.isTollFree(toDate("2013-07-01")));
        assertTrue(TollFreeDateChecker.isTollFree(toDate("2013-07-15")));
        assertTrue(TollFreeDateChecker.isTollFree(toDate("2013-07-31")));

        // Wrong year
        assertFalse(TollFreeDateChecker.isTollFree(toDate("2011-07-01")));
        assertFalse(TollFreeDateChecker.isTollFree(toDate("2012-07-02")));
        assertFalse(TollFreeDateChecker.isTollFree(toDate("2014-07-01")));
        assertFalse(TollFreeDateChecker.isTollFree(toDate("2015-07-01")));
        assertFalse(TollFreeDateChecker.isTollFree(toDate("2016-07-01")));

        // Wrong year, different day
        assertFalse(TollFreeDateChecker.isTollFree(toDate("2011-07-15")));
        assertFalse(TollFreeDateChecker.isTollFree(toDate("2012-07-16")));
        assertFalse(TollFreeDateChecker.isTollFree(toDate("2014-07-15")));
        assertFalse(TollFreeDateChecker.isTollFree(toDate("2015-07-15")));
        assertFalse(TollFreeDateChecker.isTollFree(toDate("2016-07-15")));
    }

    @Test
    public void testSpecificDays() {
        assertTrue(TollFreeDateChecker.isTollFree(toDate("2013-03-28")));
        assertTrue(TollFreeDateChecker.isTollFree(toDate("2013-11-01")));
        assertTrue(TollFreeDateChecker.isTollFree(toDate("2013-12-24")));

        // Wrong day (not weekend)
        assertFalse(TollFreeDateChecker.isTollFree(toDate("2013-03-27")));
        assertFalse(TollFreeDateChecker.isTollFree(toDate("2013-11-07")));
        assertFalse(TollFreeDateChecker.isTollFree(toDate("2013-12-23")));

        // Wrong year
        assertFalse(TollFreeDateChecker.isTollFree(toDate("2017-03-28")));
        assertFalse(TollFreeDateChecker.isTollFree(toDate("2017-11-02")));
        assertFalse(TollFreeDateChecker.isTollFree(toDate("2017-12-25")));
    }

    private LocalDate toDate(String date) {
        return LocalDate.parse(date);
    }

}
