package tooltime.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * {@code Charges} represents the charge (pricing) schedule for a tool type
 * @param type type these charges apply to
 * @param daily daily rate
 * @param weekday indicates we should charge the daily rate for week days
 * @param weekend indicates we should charge the daily rate for week end days
 * @param holiday indicates we should charge the daily rate for holidays
 *
 * @author jay
 */
@Builder
public record Charges(
        /*
        type these charges apply to
         */
        Type type,

        /*
        daily rate
         */
        BigDecimal daily,

        /*
        indicates we should charge the daily rate for week days
         */
        boolean weekday,

        /*
        indicates we should charge the daily rate for week end days
         */
        boolean weekend,

        /*
        indicates we should charge the daily rate for holidays
         */
        boolean holiday) {

    /**
     * Given this {@code Charges} object, figure the charge rate based on day type
     *
     * @param dayType type of day in question
     * @return daily rate for day (either the value {@code daily} or zero
     */
    public BigDecimal chargeFor(DayType dayType) {
        return switch (dayType) {
            case Weekday -> daily;
            case Weekend -> weekend ? daily : new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
            case Holiday -> holiday ? daily : new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
        };
    }

    @Override
    public String toString() {
        return """
                type: %s
                daily: %s
                weekday: %s
                weekend: %s
                holiday: %s
                """.formatted(type, daily, weekday, weekend, holiday);
    }
}
