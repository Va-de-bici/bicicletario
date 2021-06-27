package echo.util;

import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;
import echo.Controller;

public class JavalinApp {
    private Javalin app = 
            Javalin.create(config -> config.defaultContentType = "application/json")
                .routes(() -> {
                    path("/:echo", () -> get(Controller::getEcho));
                    path("/", ()-> get(Controller::getRoot));
                    });
                    


    public void start(int port) {
        this.app.start(port);
    }

    public void stop() {
        this.app.stop();
    }
}
