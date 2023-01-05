package vadebici.servico_aluguel.dom;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tranca {
    private String id;
    private String bicicleta;
    private String status;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBicicleta() {
        return bicicleta;
    }

    public void setBicicleta(String bicicleta) {
        this.bicicleta = bicicleta;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
