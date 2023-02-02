package vadebici.servico_externo.service.mapping;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetnetRequisicaoOrder {
    @JsonProperty("order_id")
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
