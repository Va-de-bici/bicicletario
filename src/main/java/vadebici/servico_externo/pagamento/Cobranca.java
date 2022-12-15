package vadebici.servico_externo.pagamento;

import vadebici.servico_externo.dom.CartaoCredito;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class Cobranca {

	private static final String MERCHANTID = "95061429-4514-431d-8585-befe1ae162da";
	private static final String MERCHANTKEY = "XQHDFODQYLWBYSJEEXTCRZUJGKTYUYIESBULOZIK";
	private static final String REQUISICAO = "https://apisandbox.cieloecommerce.cielo.com.br/1/sales/";

	public HttpResponse<JsonNode> validaCartaoCredito(CartaoCredito cartao) {
		TransacaoCielo transacao = new TransacaoCielo("1");
		transacao.getPayment().setAmount(1);
		transacao.getPayment().setInstallments(1);
		transacao.getPayment().setType("CreditCard");
		transacao.getPayment().getCreditCard().setBrand("Visa");
		transacao.getPayment().getCreditCard().setCardNumber(cartao.getNumero());
		transacao.getPayment().getCreditCard().setExpirationDate(cartao.getValidade());
		transacao.getPayment().getCreditCard().setSecurityCode(cartao.getCvv());

		HttpResponse<JsonNode> response = Unirest.post(REQUISICAO)
				.header("Content-Type", "application/json")
				.header("MerchantKey", MERCHANTKEY)
				.header("MerchantId", MERCHANTID)
				.body(transacao)
				.asJson();

		return response;
	}
}

