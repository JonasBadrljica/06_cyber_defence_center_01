import org.junit.jupiter.api.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestApplication {
    private Application application;

    @BeforeEach
    public void setup() {
        application = new Application();
        application.loadRecords();
    }

    @Test
    @Order(1)
    public void executeSQL01() {
        assertEquals(1000000, application.executeQuery01());
    }
}