package tooltime.service.rental;

import tooltime.model.Rental;
import tooltime.model.RentalAgreement;

/**
 * Service object for computing the {@link RentalAgreement}
 *
 * @author jay
 */
public interface RentalService {

    /**
     * Compute the agreement
     *
     * @param rental details of the rental
     * @return a full aggreement ready for the customer
     */
    RentalAgreement rent(Rental rental);
}
