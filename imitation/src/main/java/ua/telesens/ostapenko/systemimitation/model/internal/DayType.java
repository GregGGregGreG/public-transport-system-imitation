package ua.telesens.ostapenko.systemimitation.model.internal;

import java.time.DayOfWeek;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

/**
 * @author root
 * @since 29.12.15
 */
public enum DayType {
    //// FIXME: 04.01.16 Write logic from customise day
    WORKDAY, HOLIDAY, CUSTOMISE;

    public static DayType to(DayOfWeek dayOfWeek) {
        return dayOfWeek.equals(SATURDAY) || dayOfWeek.equals(SUNDAY) ? HOLIDAY : WORKDAY;
    }
}
