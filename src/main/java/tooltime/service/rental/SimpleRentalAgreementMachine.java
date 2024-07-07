package tooltime.service.rental;

import lombok.Getter;
import tooltime.model.Charges;
import tooltime.model.DayType;
import tooltime.model.Rental;
import tooltime.util.Tools;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;

/**
 * The {@code SimpleRentalAgreementMachine} class implements a rental agreement calculator by using the approach
 * of walking through the rental period day-by-day, examining each day for type-of-day and looking up the
 * corresponding charge.
 *
 * @author Jay
 */
@Getter
public class SimpleRentalAgreementMachine implements RentalAgreementCalculator {

    /**
     * Walk through each day of the rental, figuring the appropriate charge for the day type
     * Assumption based on initial requirements is:
     * start at checkout+1 and end at due date (total days to walk are: Rental days - 1)
     *
     * @param rental  details of the rental
     * @param charges schedule of charges for tool type
     */
    @Override
    public RentalAgreementCalculationResult operate(Rental rental, Charges charges) {
        RentalAgreementCalculationResult result = new RentalAgreementCalculationResult();

        // loop control meets "Count of chargeable days, from day after checkout through and including due date"
        for (int dayOfRental = 1; dayOfRental <= rental.rentalDayCount(); dayOfRental++) {
            /*
             * start of day for this day of rental
             */
            ZonedDateTime currentDate = rental.checkOutDate().plusDays(dayOfRental);

            /*
             * particular type of day this is (Weekday, Weekend, Holiday)
             */
            DayType dayType = Tools.dayTypeFor(currentDate);

            /*
             * look up this tool's charge for the type of day
             */
            BigDecimal todayCharge = charges.chargeFor(dayType);

            /*
             * tally up on if this is a chargeable day
             */
            if (todayCharge.compareTo(BigDecimal.ZERO) != 0) {
                result.incChargeableDays();
            }

            /*
             * accumulate sum of all charges (before discount)
             */
            result.add(todayCharge);

            // update our internal stats on day types
            switch (dayType) {
                case Weekday -> result.incWeekdays();
                case Weekend -> result.incWeekends();
                case Holiday -> result.incHolidays();
            }
        }
        return result;
    }


}
