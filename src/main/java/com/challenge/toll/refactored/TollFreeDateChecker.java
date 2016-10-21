package com.challenge.toll.refactored;

import com.google.common.collect.Sets;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Set;

final class TollFreeDateChecker {

    // not sure why only 2013, ideally would get this list through config, not hardcoded
    private static final Set<LocalDate> WHITELIST_FREE_DAYS = Sets.newHashSet(
            LocalDate.of(2013, 1, 1),
            LocalDate.of(2013, 3, 28),
            LocalDate.of(2013, 3, 29),
            LocalDate.of(2013, 4, 1),
            LocalDate.of(2013, 5, 1),
            LocalDate.of(2013, 5, 8),
            LocalDate.of(2013, 5, 9),
            LocalDate.of(2013, 6, 9),
            LocalDate.of(2013, 11, 1),
            LocalDate.of(2013, 12, 24),
            LocalDate.of(2013, 12, 25),
            LocalDate.of(2013, 12, 26),
            LocalDate.of(2013, 12, 31));

    private static final Set<YearMonth> WHITELIST_FREE_YEAR_MONTHS = Sets.newHashSet(
            YearMonth.of(2013, 7));

    private TollFreeDateChecker() {}

    //TODO ideally return POJO with details (e.g. result={true/false}, match={NONE,WHITELIST,WEEKEND})
    static boolean isTollFree(LocalDate date) {
        return isWeekend(date) || isWhitelistedFree(date);
    }

    private static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private static boolean isWhitelistedFree(LocalDate date) {
        return WHITELIST_FREE_DAYS.contains(date) || WHITELIST_FREE_YEAR_MONTHS.contains(YearMonth.from(date));
    }

}
