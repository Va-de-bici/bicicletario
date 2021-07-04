package echo;

import io.javalin.http.Context;

public class Controller {

    private Controller(){}

    public static void getEcho(Context ctx) {
        String echo = ctx.pathParam("echo");
        ctx.result(echo +" "+ echo +" "+ echo);
        ctx.status(200);
    }

    public static void getRoot(Context ctx) {
        ctx.status(200);
        ctx.result("Isto e um eco, digite algo a mais no caminho");
    }
}
