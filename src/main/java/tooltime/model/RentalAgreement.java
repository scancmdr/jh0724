package tooltime.model;

import lombok.Builder;
import lombok.Value;
import tooltime.service.Context;
import tooltime.util.Keys;
import tooltime.util.Tools;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * Model object representing the Rental Agreement
 *
 * @author jay
 */
@Builder
@Value // gives us Java bean semantics
public class RentalAgreement {

    /*
    rental input parameters for the rental
     */
    Rental rental;

    // these values are calculated

    /*
    Date date the rental is due back
     */
    ZonedDateTime dueDate;

    /*
    Charge daily charge for the tool type
     */
    BigDecimal dailyRentalCharge;

    /*
    Days number days being charged for (subject to constraints)
     */
    int chargeDays;

    /*
    subtotal before applying discount
    */
    BigDecimal preDiscountCharge;

    /*
    amount of the discount
     */
    BigDecimal discountAmount;

    /*
    total charge for the rental
     */
    BigDecimal finalCharge;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RentalAgreement that = (RentalAgreement) o;
        return getChargeDays() == that.getChargeDays()
               && getRental().equals(that.getRental())
               && getDueDate().equals(that.getDueDate())
               && Tools.equals(getDailyRentalCharge(),that.getDailyRentalCharge())
               && Tools.equals(getPreDiscountCharge(),that.getPreDiscountCharge())
               && Tools.equals(getDiscountAmount(),that.getDiscountAmount())
               && Tools.equals(getFinalCharge(),that.getFinalCharge());
    }

    @Override
    public int hashCode() {
        int result = getRental().hashCode();
        result = 31 * result + getDueDate().hashCode();
        result = 31 * result + getDailyRentalCharge().hashCode();
        result = 31 * result + getChargeDays();
        result = 31 * result + getPreDiscountCharge().hashCode();
        result = 31 * result + getDiscountAmount().hashCode();
        result = 31 * result + getFinalCharge().hashCode();
        return result;
    }

    @Override
    public String toString() {
        var config = Context.get().getConfigurationService();
        return """
                %s: %s
                %s: %s
                %s: %s
                %s: %s
                %s: %s
                %s: %s
                %s: %s
                %s: %s
                %s: %s
                %s: %s%%
                %s: %s
                %s: %s
                """.formatted(
                config.getMessage(Keys.TOOL_CODE).orElse("Tool code"), rental.tool().code(),
                config.getMessage(Keys.TOOL_TYPE).orElse("Tool type"), rental.tool().type(),
                config.getMessage(Keys.TOOL_BRAND).orElse("Tool brand"), rental.tool().brand(),
                config.getMessage(Keys.RENTAL_DAYS).orElse("Rental days"), rental.rentalDayCount(),
                config.getMessage(Keys.CHECKOUT_DATE).orElse("Checkout date"), Tools.dayMonthYearFormat(rental.checkOutDate()),
                config.getMessage(Keys.DUE_DATE).orElse("Due date"), Tools.dayMonthYearFormat(dueDate),
                config.getMessage(Keys.DAILY_RENTAL_CHARGE).orElse("Daily rental charge"), Tools.currencyFormat.format(dailyRentalCharge),
                config.getMessage(Keys.CHARGE_DAYS).orElse("Charge days"), chargeDays,
                config.getMessage(Keys.PREDISCOUNT_CHARGE).orElse("Pre-discount charge"), Tools.currencyFormat.format(preDiscountCharge),
                config.getMessage(Keys.DISCOUNT_PERCENT).orElse("Discount percent"), rental.percentDiscount(),
                config.getMessage(Keys.DISCOUNT_AMOUNT).orElse("Discount amount"), Tools.currencyFormat.format(discountAmount),
                config.getMessage(Keys.FINAL_CHARGE).orElse("Final charge"), Tools.currencyFormat.format(finalCharge)
        );


    }
}
