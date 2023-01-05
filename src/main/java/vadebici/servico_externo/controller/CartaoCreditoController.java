package vadebici.servico_externo.controller;

import io.javalin.http.Context;
import io.javalin.plugin.json.JavalinJson;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import vadebici.servico_externo.dom.CartaoCredito;
import vadebici.servico_externo.pagamento.Cobranca;


public class CartaoCreditoController {

	public CartaoCreditoController(){}

	public CartaoCredito retornaCartaoDeCreditoIntegracao() {
		CartaoCredito card = new CartaoCredito("Bruno Bastos", "4556346904266753", "03/2024", "118");
		return card;
	}


	public static boolean validaCartaoCredito(Context ctx) {
		String body = ctx.body();
		CartaoCredito cartao = JavalinJson.getFromJsonMapper().map(body, CartaoCredito.class);
		Cobranca validacao = new Cobranca();
		HttpResponse<JsonNode> result = validacao.validaCartaoCredito(cartao);

		if (result.getStatus() == 201) {
			ctx.status(200);
			return true;
		} else if (result.getStatus() == 402) {
			ctx.status(402);
			return false;
		} else if (result.getStatus() == 422) {
			ctx.status(422);
			return false;
		}
		return false;
	}
	
}

