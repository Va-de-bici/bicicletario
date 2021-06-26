package echo;

import static org.junit.jupiter.api.Assertions.*;

import java.io.Console;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import echo.util.JavalinApp;
import io.javalin.plugin.json.JavalinJson;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;



class BicicletaControllerTest {

    private static JavalinApp app = new JavalinApp(); // inject any dependencies you might have
    private String bikeJson = JavalinJson.toJson(BicicletaController.mock.banco);

    @BeforeAll
    static void init() {
        app.start(7010);
    }
    
    @AfterAll
    static void afterAll(){
        app.stop();
    }

    @Test
    void getEcho() {
        HttpResponse response = Unirest.get("http://localhost:7010/getEcho/Artur").asString();
        assertEquals(200, response.getStatus());
        assertEquals(response.getBody(), "Artur Artur Artur");
    }
}
