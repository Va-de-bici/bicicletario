package vadebici;

import vadebici.util.JavalinApp;

public class Main {
    public static void main(String[] args) {
        // Import Databases
        vadebici.servico_aluguel.db_in_memory.Database Database_Aluguel = null;
        vadebici.servico_equipamento.db_in_memory.Database Database_Equipamento = null;
        vadebici.servico_externo.db_in_memory.Database Database_Externo = null;
        //

        JavalinApp app = new JavalinApp();
        Database_Aluguel.carregarDados();
        Database_Equipamento.carregarDados();
        Database_Externo.carregarDados();
        app.start(getHerokuAssignedPort());

    }

    private static int getHerokuAssignedPort() {
        String herokuPort = System.getenv("PORT");
        if (herokuPort != null) {
            return Integer.parseInt(herokuPort);
        }
        return 7001;
    }
}
