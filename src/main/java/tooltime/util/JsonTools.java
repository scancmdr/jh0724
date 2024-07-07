package tooltime.util;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.cfg.ConstructorDetector;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

/**
 * JSON utilities
 *
 * @author jay
 */
public class JsonTools {

    public static final ObjectMapper jsonObjectMapper;
    public static final ObjectReader jsonReader;

    static {
        jsonObjectMapper = JsonMapper.builder()
                .constructorDetector(ConstructorDetector.USE_PROPERTIES_BASED)
                .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                //.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .build();
        jsonReader = jsonObjectMapper.reader();
    }

    public static JsonNode fromString(String json) throws IOException {
        return jsonObjectMapper.readTree(json);
    }

    public static JsonNode fromUtf8Bytes(byte[] bytes) throws IOException {
        return fromString(new String(bytes, StandardCharsets.UTF_8));
    }

    public static <Type> List<Type> asList(JsonNode node, Class<Type> type) throws IOException {
        final List<Type> list = new LinkedList<>();
        for (final JsonNode e : node) {
            list.add(jsonReader.readValue(e, type));
        } // alternatively we could have used a StreamSupport with a spliterator, but we're not expecting long lists
        return list;
    }
}
