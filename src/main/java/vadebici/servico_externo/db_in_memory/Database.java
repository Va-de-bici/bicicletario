package vadebici.servico_externo.db_in_memory;

import vadebici.servico_externo.dom.Cobranca;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class Database {

    private Database() {}
    public static final Map<String, Cobranca> TABELA_COBRANCA = new HashMap<>();

    public static final Map<String, Cobranca> TABELA_FILA_COBRANCA = new HashMap<>();

    public static void limpar() {
        TABELA_COBRANCA.clear();
    }

    public static void carregarDados() {
        limpar();
        Cobranca cobranca = new Cobranca();
        cobranca.setId("123");
        cobranca.setValor(123.0);
        cobranca.setCiclista("123");
        cobranca.setStatus("APPROVED");
        cobranca.setHoraSolicitacao(new GregorianCalendar(2020, Calendar.JANUARY, 1, 12, 30, 0).getTime());
        cobranca.setHoraFinalizacao(new GregorianCalendar(2020, Calendar.JANUARY, 1, 12, 30, 0).getTime());
        TABELA_COBRANCA.put(cobranca.getId(), cobranca);
    }
}
