package vadebici;

import vadebici.util.JavalinApp;

public class Main {
    public static void main(String[] args) {
        // Import Databases
        vadebici.servico_aluguel.db_in_memory.Database Database_Aluguel = null;
        vadebici.servico_equipamento.db_in_memory.Database Database_Equipamento = null;

        JavalinApp app = new JavalinApp();
        Database_Aluguel.carregarDados();
        Database_Equipamento.carregarDados();
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
