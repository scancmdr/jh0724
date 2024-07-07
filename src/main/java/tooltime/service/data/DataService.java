package tooltime.service.data;

import tooltime.model.Charges;
import tooltime.model.Tool;
import tooltime.model.Type;

import java.util.Optional;

/**
 * Service object with commonly used lookup routines. Ideally this would abstract access to a backend local or remote
 * datastore.
 *
 * @author jay
 */
public interface DataService {

    /**
     * Obtain the tool given a code
     *
     * @param code code for a tool
     * @return the corresponding tool, if found
     */
    Optional<Tool> toolForCode(String code);

    /**
     * Obtain the charge schedule for a tool type
     *
     * @param type type of tool
     * @return the charges, if found
     */
    Optional<Charges> scheduleForType(Type type);
}
