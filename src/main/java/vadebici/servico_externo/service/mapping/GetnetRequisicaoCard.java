package vadebici.servico_externo.service.mapping;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetnetRequisicaoCard {
    @JsonProperty("number_token")
    private String number_token;
    @JsonProperty("expiration_month")
    private String expiration_month;
    @JsonProperty("expiration_year")
    private String expiration_year;
    @JsonProperty("security_code")
    private String security_code;

    private String cardholder_name;

    public String getCardholder_name() {
        return cardholder_name;
    }

    public void setCardholder_name(String cardholder_name) {
        this.cardholder_name = cardholder_name;
    }

    public String getNumber_token() {
        return number_token;
    }

    public void setNumber_token(String number_token) {
        this.number_token = number_token;
    }

    public String getExpiration_month() {
        return expiration_month;
    }

    public void setExpiration_month(String expiration_month) {
        this.expiration_month = expiration_month;
    }

    public String getExpiration_year() {
        return expiration_year;
    }

    public void setExpiration_year(String expiration_year) {
        this.expiration_year = expiration_year;
    }

    public String getSecurity_code() {
        return security_code;
    }

    public void setSecurity_code(String security_code) {
        this.security_code = security_code;
    }
}

