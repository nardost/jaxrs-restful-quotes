package edu.depaul.ntessema.jaxrs.data.model;

public class ErrorMessage {

    private Integer errorCode;
    private String errorMessage;

    public ErrorMessage() {
    }

    public ErrorMessage(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
