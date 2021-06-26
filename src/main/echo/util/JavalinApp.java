package echo.util;

import io.javalin.Javalin;
import echo.Controller;

public class JavalinApp {
    private Javalin app = 
            Javalin.create(config -> {
                config.registerPlugin(getConfiguredOpenApiPlugin());
                config.defaultContentType = "application/json";
            }).routes(() -> {
                path("/", () -> {
                        get(Controller::getEcho);
                    });
            });

    public void start(int port) {
        this.app.start(port);
    }

    public void stop() {
        this.app.stop();
    }
}
