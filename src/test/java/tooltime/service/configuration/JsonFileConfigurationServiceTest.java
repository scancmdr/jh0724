package tooltime.service.configuration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tooltime.model.Tool;
import tooltime.util.Keys;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonFileConfigurationServiceTest {
    JsonFileConfigurationService service;

    @BeforeEach
    void setUp() throws IOException {
        service = new JsonFileConfigurationService();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getObjects() throws IOException {
        List<Tool> tools = service.getObjects("tools",Tool.class);
        assertEquals(4, tools.size());

    }

    @Test
    void getMessage() {
        Optional<String> invalidDayQ = service.getMessage(Keys.INVALID_DAY_COUNT);
        assertTrue(invalidDayQ.isPresent());
        assertEquals("Rental day count is out of valid range (1 or more)", invalidDayQ.get());
    }
}