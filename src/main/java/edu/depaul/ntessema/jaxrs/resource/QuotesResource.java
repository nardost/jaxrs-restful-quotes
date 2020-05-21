package edu.depaul.ntessema.jaxrs.resource;

import edu.depaul.ntessema.jaxrs.model.Quote;
import edu.depaul.ntessema.jaxrs.service.QuoteService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/quotes")
public class QuotesResource {

    private final QuoteService quoteService = new QuoteService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Quote> getQuotes() {
        return quoteService.getAllQuotes();
    }
}
