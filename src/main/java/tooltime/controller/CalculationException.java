package tooltime.controller;

import lombok.Getter;
import tooltime.model.Rental;

/**
 * {@code CalculationException} represents an error condition during calculation of the rental agreement
 *
 * @author jay
 */
public class CalculationException extends Exception {
    @Getter
    Rental rental;

    public CalculationException(String message, Exception e, Rental rental) {
        super(message, e);
        this.rental = rental;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
