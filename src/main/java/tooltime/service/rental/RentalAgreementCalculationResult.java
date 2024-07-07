package tooltime.service.rental;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Results from a Rental Agreement calculation. All fields are calculated.
 *
 * @author jay
 */
@Getter @Setter
public class RentalAgreementCalculationResult {

    /**
     * number of days in rental that have a >0 charge
     */
    int chargeableDays;

    /**
     * we will accumulate daily charges in this field
     */
    BigDecimal preDiscountCharge = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);

    /**
     * track the count of day types for the duration of the rental, for unit testing
     */
    int weekdays;
    int weekends;
    int holidays;

    public void incChargeableDays() {
        chargeableDays++;
    }
    public void incWeekdays() {
        weekdays++;
    }
    public void incWeekends() {
        weekends++;
    }
    public void incHolidays() {
        holidays++;
    }

    public void add(BigDecimal todayCharge) {
        this.preDiscountCharge = preDiscountCharge.add(todayCharge);
    }
}
