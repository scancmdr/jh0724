package tooltime.service.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import tooltime.util.JsonTools;
import tooltime.util.Tools;

import java.io.IOException;
import java.util.*;

/**
 * {@code JsonFileConfigurationService} is a primary implementation of {@link ConfigurationService}
 * that utilizes a JSON file to load data and configuration settings
 *
 * @author jay
 */
public class JsonFileConfigurationService implements ConfigurationService {

    /*
    file loaded into a JSON object node
     */
    JsonNode config;

    /**
     * ctor that will load from file. Here we're loading from the classpath, in the future we might
     * want to check the local working directory for this file, and use that instead (override), allowing
     * a user to update or add new tools, types, and charges.
     * @throws IOException upon file I/O errors
     */
    public JsonFileConfigurationService() throws IOException {
        config = JsonTools.fromUtf8Bytes(Tools.getResourceFromClasspath("/config.json"));
    }

    /**
     * Fish the data objects out of the JSON node
     *
     * @param fieldName name of field at top level in config file
     * @param type type to utilize when instantiating these objects
     * @return list of data objects
     * @param <Type>
     * @throws IOException
     */
    @Override
    public <Type> List<Type> getObjects(String fieldName, Class<Type> type) throws IOException {
        if (config.has(fieldName)) {
            final JsonNode node = config.get(fieldName);
            if (node.isArray()) {
                return JsonTools.asList(node, type);
            }
        }
        return Collections.emptyList();
    }

    /**
     * @param key name of entry (see {@link tooltime.util.Keys}
     * @return
     */
    @Override
    public Optional<String> getMessage(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle("resource", Locale.getDefault());
        return bundle.containsKey(key) ? Optional.of(bundle.getString(key)) : Optional.empty();
    }


}
