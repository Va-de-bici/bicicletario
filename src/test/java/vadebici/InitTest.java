package vadebici;

import org.junit.jupiter.api.Test;
import vadebici.util.JavalinApp;

public class InitTest {
    private final JavalinApp javalinApp = new JavalinApp();

    @Test
    void runServer() {
        javalinApp.start(9000);
        javalinApp.stop();
    }
}
