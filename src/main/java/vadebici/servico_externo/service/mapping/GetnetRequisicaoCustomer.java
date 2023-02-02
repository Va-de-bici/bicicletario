package vadebici.servico_externo.service.mapping;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class GetnetRequisicaoCustomer {
    @JsonProperty("customer_id")
    private String customerId;
    @JsonProperty("billing_address")
    private Map<Object, Object> billingAddress;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Map<Object, Object> getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Map<Object, Object> billingAddress) {
        this.billingAddress = billingAddress;
    }
}

