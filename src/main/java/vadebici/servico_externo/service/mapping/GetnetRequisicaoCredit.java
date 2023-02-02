package vadebici.servico_externo.service.mapping;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetnetRequisicaoCredit {
    private boolean delayed;
    @JsonProperty("save_card_data")
    private boolean saveCardData;
    @JsonProperty("transaction_type")
    private String transactionType;
    @JsonProperty(value = "number_installments")
    private int numberInstallments;
    private GetnetRequisicaoCard card;

    public boolean isDelayed() {
        return delayed;
    }

    public void setDelayed(boolean delayed) {
        this.delayed = delayed;
    }

    public boolean isSaveCardData() {
        return saveCardData;
    }

    public void setSaveCardData(boolean saveCardData) {
        this.saveCardData = saveCardData;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public int getNumberInstallments() {
        return numberInstallments;
    }

    public void setNumberInstallments(int numberInstallments) {
        this.numberInstallments = numberInstallments;
    }

    public GetnetRequisicaoCard getCard() {
        return card;
    }

    public void setCard(GetnetRequisicaoCard card) {
        this.card = card;
    }
}
