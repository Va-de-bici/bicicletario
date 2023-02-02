package vadebici.servico_externo.service.mapping;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GetnetErrorResponse {
    private String message;
    private String name;
    @JsonProperty("status_code")
    private String statusCode;
    private List<GetnetErrorResponseDetails> details;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public List<GetnetErrorResponseDetails> getDetails() {
        return details;
    }

    public void setDetails(List<GetnetErrorResponseDetails> details) {
        this.details = details;
    }
}
