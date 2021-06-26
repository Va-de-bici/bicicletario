package echo;

import io.javalin.http.Context;

public class Controller {

    private Controller(){}

    public static void getEcho(Context ctx) {
        String echo = ctx.pathParam("echo");
        ctx.result(this.echo +" "+ this.echo +" "+ this.echo);
    }
}
