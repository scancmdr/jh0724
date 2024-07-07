package tooltime.controller;

import tooltime.model.Rental;
import tooltime.model.RentalAgreement;
import tooltime.model.Tool;
import tooltime.service.Context;
import tooltime.util.Keys;

import java.util.Optional;

/**
 * {@code ToolRentalController} encapsulates business logic & validation, delegates computation to rental service
 *
 * @author jay
 */
public class ToolRentalController {

    /**
     * calculate the Rental Agreement for a given Rental
     *
     * @param rental input parmeters for the rental
     * @return a Rental Agreement computed from the {@code Rental}
     * @throws ValidationException upon invalid inputs
     * @throws CalculationException upon encountering an error during calculation
     */
    public RentalAgreement calculate(Rental rental) throws ValidationException, CalculationException {

        // validations
        if (rental.rentalDayCount() < 1) {
            throw new ValidationException(Keys.INVALID_DAY_COUNT);
        }

        if ((rental.percentDiscount() < 0) || (rental.percentDiscount() > 100)) {
            throw new ValidationException(Keys.INVALID_DISCOUNT);
        }

        // calculations
        try {
            return Context.get().getRentalService().rent(rental);
        } catch (Exception e) {
            // capture and normalize errors to caller
            throw new CalculationException("unable to calculate rental - " + e.getClass().getSimpleName() + " " + e.getMessage(), e, rental);
        }
    }

    public Optional<Tool> toolForCode(String toolCode) {
        return Context.get().getDataService().toolForCode(toolCode);
    }
}