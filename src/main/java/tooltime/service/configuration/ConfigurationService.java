package tooltime.service.configuration;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Service providing configuration data
 *
 * @author jay
 */
public interface ConfigurationService {

    /**
     * Load typed data objects from a field in file <pre>config.json</pre>
     *
     * @param fieldName name of field at top level in config file
     * @param type type to utilize when instantiating these objects
     * @return list of data objects
     * @param <Type> must match deserialization requirements dictated by config file content
     * @throws IOException upon error (file I/O, etc)
     */
    <Type> List<Type> getObjects(String fieldName, Class<Type> type) throws IOException;

    /**
     * Get a resource bundle message
     *
     * @param key name of entry (see {@link tooltime.util.Keys}
     * @return value for key, if found
     */
    Optional<String> getMessage(String key);
}
