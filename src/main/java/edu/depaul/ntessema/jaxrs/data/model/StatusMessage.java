package edu.depaul.ntessema.jaxrs.data.model;

public class StatusMessage {

    private Integer statusCode;
    private String statusMessage;

    public StatusMessage() {
    }

    public StatusMessage(Integer statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
