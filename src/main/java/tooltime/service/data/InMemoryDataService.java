package tooltime.service.data;

import lombok.extern.slf4j.Slf4j;
import tooltime.model.Charges;
import tooltime.model.Tool;
import tooltime.model.Type;
import tooltime.service.configuration.ConfigurationService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory variant of the Data Service
 *
 * @author jay
 */
@Slf4j
public class InMemoryDataService implements DataService {

    // storage and lookup
    protected Map<String /* code */, Tool> toolByCode = new HashMap<>();
    protected Map<Type /* type of tool */, Charges> chargesByType = new HashMap<>();

    /**
     * ctor that loads data using {@link ConfigurationService}
     *
     * @param configurationService configuration to source data from
     * @throws IOException upon any error originating from configuration service
     */
    public InMemoryDataService(ConfigurationService configurationService) throws IOException {
        for (Tool tool : configurationService.getObjects("tools", Tool.class)) {
            toolByCode.put(tool.code(), tool);
        }
        for (Charges charges : configurationService.getObjects("charges", Charges.class)) {
            chargesByType.put(charges.type(), charges);
        }
    }

    @Override
    public Optional<Tool> toolForCode(String code) {
        return Optional.ofNullable(toolByCode.get(code));
    }

    @Override
    public Optional<Charges> scheduleForType(Type type) {
        return Optional.ofNullable(chargesByType.get(type));
    }
}
