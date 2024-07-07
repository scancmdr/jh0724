package tooltime.service.rental;

import tooltime.model.Charges;
import tooltime.model.Rental;
import tooltime.model.RentalAgreement;
import tooltime.service.Context;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * Simple implementation of {@link RentalService} that uses a composite polymorphic calculator to
 * produce a {@link RentalAgreement}.
 *
 * @author jay
 */
public class SimpleRentalService implements RentalService {

    static final BigDecimal B100 = new BigDecimal(100);

    /**
     * Delegate the calculation to a calculator {@code RentalAgreementCalculator}. This field typically would be
     * injected by a factory or container (e.g. Spring) depending upon configuration or preferences. For example, if
     * a store preferred to include the check out day as a potential chargeable day we could provide that logic in
     * a different implementation of the calculator.
     */
    private final RentalAgreementCalculator calculator = new SimpleRentalAgreementMachine();

    /* (non-Javadoc)
     * @see com.cyglass.keel.load.config.Configuration#workers()
     */


    /**
     * Compute the rental agreement
     *
     * @param rental details of the rental
     * @return full agreement
     */
    @Override
    public RentalAgreement rent(Rental rental) {

        // obtain the schedule of charges for tool type
        Optional<Charges> scheduleQ = Context.get().getDataService().scheduleForType(rental.tool().type());
        if (scheduleQ.isEmpty()) {
            throw new RuntimeException("no schedule for type " + rental.tool().type());
        }
        Charges charges = scheduleQ.get();

        // basic calculations
        RentalAgreementCalculationResult result = calculator.operate(rental, charges);

        // calc discount amount = pre-discount-charge * percent-discount / 100
        BigDecimal discountAmount = calculateDiscount(result.getPreDiscountCharge(), rental.percentDiscount());

        // final assembly
        return RentalAgreement.builder()
                .rental(rental)
                .dueDate(rental.checkOutDate().plusDays(rental.rentalDayCount()))
                .dailyRentalCharge(charges.daily())
                .chargeDays(result.getChargeableDays())
                .preDiscountCharge(result.getPreDiscountCharge())
                .discountAmount(discountAmount)
                .finalCharge(result.getPreDiscountCharge().subtract(discountAmount))
                .build();
    }

    /**
     * Compute the discount using formula:
     * <blockquote><pre>
     *   discount = (subtotal * percent discount) / 100
     * </pre></blockquote>
     * <p>
     * We're saving the division as the last operation because it usually reduces scale, and we want to save that
     * effect for last. If we were using {@code double} for our number type this would be the preferred logic
     * as the whole number part of a {@code double} should be maximized first because it is represented (largely)
     * without error in floating point implementations.
     * Alternatively, we could use:
     * <blockquote><pre>
     *   discount = (percent discount / 100) * subtotal
     * </pre></blockquote>
     * And supply an explicit scale to the division operation and achieve a similar result.
     *
     * @param subtotal
     * @param discountPercent
     * @return
     */
    protected BigDecimal calculateDiscount(BigDecimal subtotal, int discountPercent) {
        return subtotal.multiply(new BigDecimal(discountPercent)).divide(B100, 2, RoundingMode.HALF_UP);
    }
}
