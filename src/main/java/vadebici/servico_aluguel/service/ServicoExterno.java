package vadebici.servico_aluguel.service;

import vadebici.servico_aluguel.dom.*;
import vadebici.servico_aluguel.dom.dto.CobrancaJsonApi;

import java.util.UUID;

public class ServicoExterno {
    public boolean validaCartao(MeioDePagamento meioDePagamento) {
        return meioDePagamento != null;
    }

    public boolean enviaEmail(Email email) {
        return email != null;
    }

    public Cobranca realizaCobranca(CobrancaJsonApi cobrancaJsonApi) {
        double valor = cobrancaJsonApi.getValor();
        Cobranca cobranca = new Cobranca();
        cobranca.setId(UUID.randomUUID().toString());
        cobranca.setStatus("Aprovado");
        cobranca.setValor(valor);
        return cobranca;
    }

}
