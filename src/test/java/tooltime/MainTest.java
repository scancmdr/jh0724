package tooltime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    Main main;
    CommandLine cmd;

    @BeforeEach
    void setUp() {
        main = new Main();
        cmd = new CommandLine(main);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testMain() {
        int exitCode = cmd.execute("--code=LADW","--days=1", "--discount=1", "--checkoutDate=09/10/24");
        assertEquals(0, exitCode);
    }
}