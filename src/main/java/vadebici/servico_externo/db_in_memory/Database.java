package vadebici.servico_externo.db_in_memory;

import vadebici.servico_externo.dom.*;

import java.util.*;

public class Database {

    private Database(){}
    
    public static final Map<String, Email> EMAIL_TABLE = new HashMap<>();

    public static void limpar() {
        EMAIL_TABLE.clear();
    }

    public static void carregarDados() {
        limpar();
        Email email = new Email();
        email.setEmail("bruno@unirio.br");
        email.setMensagem("Teste externo");
        EMAIL_TABLE.put(email.getEmail(), email);
    }
}
