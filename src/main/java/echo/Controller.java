package echo;

import io.javalin.http.Context;

public class Controller {

    private Controller(){}

    public static void getEcho(Context ctx) {
        String echo = ctx.pathParam("echo");
        ctx.result(echo +" "+ echo +" "+ echo);
    }

    public static void getRoot(Context ctx) {
        ctx.result("Isto e um eco, digite algo a mais no caminho");
    }
}
