package vadebici.servico_externo.pagamento;

import io.javalin.http.Context;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import vadebici.servico_externo.controller.CartaoCreditoController;
import vadebici.servico_externo.dom.CartaoCredito;
import vadebici.servico_externo.dom.CobrancaD;
import vadebici.servico_externo.form.CobrancaForm;


public class Cobranca {

	private static final String MERCHANTID = "95061429-4514-431d-8585-befe1ae162da";
	private static final String MERCHANTKEY = "XQHDFODQYLWBYSJEEXTCRZUJGKTYUYIESBULOZIK";
	private static final String REQUISICAO = "https://apisandbox.cieloecommerce.cielo.com.br/1/sales/";

	private CartaoCredito card;
	private CobrancaD cobranca;

	public Cobranca (){
		
	}

	public Cobranca (CartaoCredito card, CobrancaD cobranca){
		this.card = card;
		this.cobranca = cobranca;
	}

	
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

	/*public static void getCobranca(Context ctx) throws GenericApiException {
		Cobranca daoCobranca = new Cobranca();
		CobrancaD daoCobrancaD = daoCobranca.consultarCobrancaCobrancaById(Objects.requireNonNull(ctx.pathParam("id")));

		if (daoCobranca != null) {
			ctx.status(200);
			ctx.json(daoCobranca);
		} else {
			GenericApiException cobNotFound = new GenericApiException();
			ctx.html(cobNotFound.getMessageCobrancaNotFound());
			ctx.status(404);
		}
	}*/

	public static void realizaCobranca(Context ctx) {
		CobrancaD cobrancaForm = ctx.bodyAsClass(CobrancaForm.class).deserializePostCobranca();
		CartaoCreditoController cardController = new CartaoCreditoController();
		CartaoCredito cardRetornado = cardController.retornaCartaoDeCreditoIntegracao();
		Cobranca send = new Cobranca(cardRetornado, cobrancaForm);

		HttpResponse<JsonNode> response = send.sendCobranca();
		ctx.json(response.getBody());

		if (response.getStatus() == 201) {
			ctx.status(201);
			return;
		} else {
			ctx.status(422);
		}
	}

	/*public CobrancaD consultarCobrancaCobrancaById(String id) throws GenericApiException {

		ArrayList<CobrancaD> cobrancasIdLista = retornarCobrancas();
		CobrancaD cobrancaId = null;

		for (int i = 0; i < cobrancasIdLista.size(); i++) {
			if (cobrancasIdLista.get(i).getId().equals(id)) {
				cobrancaId = cobrancasIdLista.get(i);
				break;
			}
		}

		if (cobrancaId != null) {
			return cobrancaId;
		} else {
			GenericApiException error = new GenericApiException();
			throw new GenericApiException(error.getMessageCobrancaNotFound());
		}
	}*/

	public HttpResponse<JsonNode> sendCobranca() {
		TransacaoCielo transaction = new TransacaoCielo("1");
		transaction.getCustomer().setName(this.card.getNomeTitular());
		transaction.getPayment().setAmount(cobranca.getValor());
		transaction.getPayment().setInstallments(1);
		transaction.getPayment().setType("CreditCard");
		transaction.getPayment().getCreditCard().setBrand("Visa");
		transaction.getPayment().getCreditCard().setCardNumber(card.getNumero());
		transaction.getPayment().getCreditCard().setExpirationDate(card.getValidade());
		transaction.getPayment().getCreditCard().setSecurityCode(card.getCvv());
		HttpResponse<JsonNode> response = Unirest.post(REQUISICAO).header
				("Content-Type", "application/json").header("MerchantKey", MERCHANTKEY)
				.header("MerchantId", MERCHANTID).body(transaction).asJson();
		return response;
	}


	/*public ArrayList<CobrancaD> retornarCobrancas() {

		ArrayList<CobrancaD> cobrancasRetornadas = new ArrayList<CobrancaD>();
		cobrancasRetornadas.add(new CobrancaD("3fa85f64-5717-4562-b3fc-2c963f66afa6", 1));
		cobrancasRetornadas
				.add(new CobrancaD("5fc808e4-d59d-4556-8ee7-b2d754b8cdf1", "3fa85f64-5717-4562-b3fc-2c963f66afa7",
						"2021-09-03T13:23:41.414Z", "2021-09-03T13:25:29.195Z", 7, "Pendente"));
		
		for (int i = 0; i < cobrancasRetornadas.size(); i++) {
			FilaCobranca.inserir(cobrancasRetornadas.get(i));
		}
		
		return cobrancasRetornadas;
	}
*/


}

