package tooltime.service.rental;

import tooltime.model.Charges;
import tooltime.model.Rental;

import java.math.BigDecimal;

/**
 * {@code RentalAgreementCalculator} provides a contract for computing parts of a rental agreement
 * for now we just need {@code chargeableDays} and {@code preDiscountCharge}
 *
 * @author Jay
 */
public interface RentalAgreementCalculator {
    /**
     * perform the calculation, delegating the method to implementation
     */
    RentalAgreementCalculationResult operate(Rental rental, Charges charges);
}
