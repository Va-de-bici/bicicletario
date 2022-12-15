package vadebici.servico_externo.controller;

import io.javalin.http.Context;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import vadebici.servico_externo.dom.CartaoCredito;
import vadebici.servico_externo.form.CartaoCreditoForm;
import vadebici.servico_externo.pagamento.Cobranca;


public class CartaoCreditoController {

	private CartaoCreditoController(){}

	public static boolean validaCartaoCredito(Context ctx) {
		CartaoCredito cartao = ctx.bodyAsClass(CartaoCreditoForm.class).deserialize();
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

