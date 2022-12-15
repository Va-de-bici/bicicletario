package vadebici.servico_externo.controller;

import io.javalin.http.Context;
import io.javalin.plugin.json.JavalinJson;
import vadebici.servico_externo.dom.Email;
import vadebici.servico_externo.service.ServiceEmail;

public class EmailController {

    private EmailController() {}

    private static final ServiceEmail serviceEmail = new ServiceEmail();

    public static void enviaEmail(Context ctx) {
        String body = ctx.body();
        Email email = JavalinJson.getFromJsonMapper().map(body, Email.class);
        serviceEmail.enviaMensagem(email);
        ctx.status(200);

    }
}

