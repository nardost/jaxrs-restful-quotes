package edu.depaul.ntessema.jaxrs.model;

public class Quote {

    private Integer id;
    private String quote;

    public Quote() {
    }

    public Quote(String quote) {
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
