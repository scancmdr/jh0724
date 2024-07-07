package tooltime.service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import tooltime.service.configuration.ConfigurationService;
import tooltime.service.configuration.JsonFileConfigurationService;
import tooltime.service.data.DataService;
import tooltime.service.data.InMemoryDataService;
import tooltime.service.rental.RentalService;
import tooltime.service.rental.SimpleRentalService;

import java.io.IOException;

/**
 * Service locator pattern (simplistic). Allows access to service objects using an application-wide singleton.
 *
 * @author jay
 */
@FieldDefaults(level = AccessLevel.PROTECTED)
@Getter
public class Context {

    final DataService dataService;
    final RentalService rentalService;
    final ConfigurationService configurationService;

    private Context() throws IOException {
        configurationService = new JsonFileConfigurationService();
        dataService = new InMemoryDataService(configurationService);
        rentalService = new SimpleRentalService();
    }

    public static Context get() {
        return Singleton.INSTANCE;
    }

    /*
     * For use as a singleton - Bill Pugh design (https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom)
     */
    private static class Singleton {
        static final Context INSTANCE;

        static {
            try {
                INSTANCE = new Context();
            } catch (IOException e) {
                throw new RuntimeException("unable to configure services, check configuration source accessibility", e);
            }
        }
    }

}
