package vadebici.servico_externo.service.mapping;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetnetErrorResponseDetails {
    private String status;
    @JsonProperty("error_code")
    private String errorCode;
    private String description;
    @JsonProperty("description_detail")
    private String descriptionDetail;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionDetail() {
        return descriptionDetail;
    }

    public void setDescriptionDetail(String descriptionDetail) {
        this.descriptionDetail = descriptionDetail;
    }
}
