package edu.depaul.ntessema.jaxrs.data.model;

/**
 * The quote resource entity.
 */
public class Quote {

    private Integer id;
    private String quote;

    /*
     * No argument constructor should be there
     * for serialization/de-serialization.
     */
    public Quote() {
    }

    public Quote(String quote) {
        this.quote = quote;
    }

    public Quote(Integer id, String quote) {
        this.id = id;
        this.quote = quote;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}
