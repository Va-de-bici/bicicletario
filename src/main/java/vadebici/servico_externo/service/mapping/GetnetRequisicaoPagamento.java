package vadebici.servico_externo.service.mapping;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GetnetRequisicaoPagamento {
    @JsonProperty("seller_id")
    private String sellerId;
    private String amount;
    private GetnetRequisicaoOrder order;
    private GetnetRequisicaoCustomer customer;
    @JsonProperty("credit")
    private GetnetRequisicaoCredit credit;

    private List<GetnetRequisicaoShippingInfo> shippings;

    public List<GetnetRequisicaoShippingInfo> getShippings() {
        return shippings;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public void setShippings(List<GetnetRequisicaoShippingInfo> shippings) {
        this.shippings = shippings;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public GetnetRequisicaoOrder getOrder() {
        return order;
    }

    public void setOrder(GetnetRequisicaoOrder order) {
        this.order = order;
    }

    public GetnetRequisicaoCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(GetnetRequisicaoCustomer customer) {
        this.customer = customer;
    }

    public GetnetRequisicaoCredit getCredit() {
        return credit;
    }

    public void setCredit(GetnetRequisicaoCredit credit) {
        this.credit = credit;
    }
}
