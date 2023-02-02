package vadebici.servico_externo.dom;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ciclista {
    @NotBlank(message = "O campo 'id' é obrigatório")
    private String id;
    @NotNull(message = "O campo 'cartao' é obrigatório")
    @JsonProperty("meioDePagamento")
    private Cartao cartao;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }
}
