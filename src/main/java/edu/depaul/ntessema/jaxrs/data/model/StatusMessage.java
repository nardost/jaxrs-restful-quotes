package edu.depaul.ntessema.jaxrs.data.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This entity is used to send status messages
 * such as errors and success messages.
 *
 * The JAXB annotation is needed only to produce XML content
 */
@XmlRootElement
public class StatusMessage {

    private Integer statusCode;
    private String statusMessage;

    /*
     * No argument constructor should be there
     * for serialization/de-serialization.
     */
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
