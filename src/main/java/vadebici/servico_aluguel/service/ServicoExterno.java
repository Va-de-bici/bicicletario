package vadebici.servico_aluguel.service;

import vadebici.servico_aluguel.dom.*;

public class ServicoExterno {
    public boolean validaCartao(MeioDePagamento meioDePagamento) {
        return meioDePagamento != null;
    }

    public boolean enviarEmail(Email email) {
        return email != null;
    }

}
