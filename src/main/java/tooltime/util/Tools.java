package tooltime.util;

import tooltime.model.DayType;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * General utilities
 *
 * @author jay
 */
public class Tools {
    public static final DateTimeFormatter monthDayYearFormat = DateTimeFormatter.ofPattern("MM/dd/yy");
    public static final DecimalFormat currencyFormat = new DecimalFormat("$#,###.##");

    public static ZonedDateTime dayMonthYearFormat(String date, String timeZoneId) {
        ZoneId zoneId = ZoneId.of(timeZoneId);
        LocalDate localDate = LocalDate.parse(date, monthDayYearFormat);
        LocalDateTime localDateTime = localDate.atTime(LocalTime.MIDNIGHT); // start of day
        return localDateTime.atZone(zoneId);
    }

    public static String dayMonthYearFormat(ZonedDateTime date) {
        return monthDayYearFormat.format(date);
    }

    /**
     * test if date is the Fourth of July, by matching on July and day = 4
     *
     * @param date the day in question
     * @return
     */
    public static boolean isFourthOfJuly(ZonedDateTime date) {
        return (date.getDayOfMonth() == 4) && (date.getMonthValue() == 7);
    }

    public static boolean isFourthOfJulyRelative(ZonedDateTime date, int adjust) {
        return (date.getDayOfMonth() == (4 + adjust)) && (date.getMonthValue() == 7);
    }

    /**
     * test if date is labor day, by matching on Monday, September, and day between 1-7
     *
     * @param date the date in question
     * @return
     */
    public static boolean isLaborDay(ZonedDateTime date) {
        int day = date.getDayOfMonth();
        return (date.getMonth() == Month.SEPTEMBER)
               && (date.getDayOfWeek() == DayOfWeek.MONDAY)
               && (day < 8);
    }

    public static ZonedDateTime toZonedDateTime(LocalDate localDate, String timeZoneId) {
        ZoneId timeZone = ZoneId.of(timeZoneId);
        return localDate.atStartOfDay(timeZone);
    }

    /**
     * Determine the day type for specified date
     *
     * @param localDate  unzoned-day
     * @param timeZoneId time zone for location
     * @return Day Type (weekday, weekend, or holiday)
     */
    public static DayType dayTypeFor(LocalDate localDate, String timeZoneId) {
        ZoneId timeZone = ZoneId.of(timeZoneId);
        return dayTypeFor(localDate.atStartOfDay(timeZone));
    }

    /**
     * Determine the day type for specified date
     *
     * @param timeZonedDate zoned date
     * @return Day Type (weekday, weekend, or holiday)
     */
    public static DayType dayTypeFor(ZonedDateTime timeZonedDate) {

        // weekday or weekend?
        DayOfWeek dayOfWeek = timeZonedDate.getDayOfWeek();
        final DayType dt = switch (dayOfWeek) {
            case SUNDAY, SATURDAY -> DayType.Weekend;
            default -> DayType.Weekday;
        };

        // detect our holiday, anything more elaborate here should be factored out to a "holiday service"
        // e.g. some deterministic calendar algorithm, or even simpler - a listing of all holidays for the next 100+ years
        // (because, say, the computations for Easter, and other holy days can be gruesome)
        boolean holiday =
                // straight-up: is today Labor day?
                isLaborDay(timeZonedDate)
                // is today the 4th of July AND NOT the weekend?
                || (dt == DayType.Weekday && isFourthOfJuly(timeZonedDate))
                // is today the Friday before a Saturday 4th
                || (dayOfWeek == DayOfWeek.FRIDAY && isFourthOfJulyRelative(timeZonedDate, -1))
                // is today the Monday after the Sunday 4th
                || (dayOfWeek == DayOfWeek.MONDAY && isFourthOfJulyRelative(timeZonedDate, +1));
        return holiday ? DayType.Holiday : dt;
    }

    public static byte[] getResourceFromClasspath(String name) throws IOException {
        return getResourceFromClasspath(name, Tools.class);
    }

    public static byte[] getResourceFromClasspath(String name, Class<?> type) throws IOException {
        try (InputStream is = type.getResourceAsStream(name)) {
            if (is == null) {
                throw new IOException("Resource not found: " + name + " (in " + type.getSimpleName() + ")");
            }
            return is.readAllBytes();
        }
    }

    /**
     * BigDecimal uses scale and value, which can be different for the same value
     *
     * @param left
     * @param right
     * @return
     */
    public static boolean equals(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) == 0;
    }
}
