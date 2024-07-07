package tooltime.model;

import lombok.Builder;
import tooltime.util.Tools;

import java.time.ZonedDateTime;

/**
 * Rental model object - represents the input parameters for a tool rental
 *
 * @param tool Tool we want to rent
 * @param rentalDayCount duration of rental in days
 * @param percentDiscount percent discount, valid range of 0-100
 * @param checkOutDate day of checkout for tool (time should be start of day)
 *
 * @author jay
 */
@Builder
public record Rental(
        /*
          Tool we want to rent
         */
        Tool tool,

        /*
          duration of rental in days
         */
        int rentalDayCount,

        /*
          percent discount, valid range of 0-100
         */
        int percentDiscount,

        /*
          day of checkout for tool, should be start of day
         */
        ZonedDateTime checkOutDate
) {

    @Override
    public String toString() {
        return """
                %s
                rentalDayCount: %d
                percentDiscount: %d%%
                checkOutDate: %s
                """.formatted(tool.toString().trim(), rentalDayCount, percentDiscount, Tools.dayMonthYearFormat(checkOutDate));
    }
}