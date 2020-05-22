package edu.depaul.ntessema.jaxrs.resource;

import edu.depaul.ntessema.jaxrs.data.model.Quote;
import edu.depaul.ntessema.jaxrs.data.service.QuoteService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

@Path("/quotes")
public class QuotesResource {

    private final QuoteService quoteService = new QuoteService();
    private final int DEFAULT_PER_PAGE = 5;

    @GET
    @Produces(APPLICATION_JSON)
    public List<Quote> getAllQuotes(
            @QueryParam("page") Integer page,
            @QueryParam("per_page") Integer perPage) {

        if(page == null) {
            return quoteService.getAllQuotes();
        }
        if(perPage == null) {
            perPage = DEFAULT_PER_PAGE;
        }
        return quoteService.getQuotesPerPage(page, perPage);
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/{id}")
    public Quote getQuote(@PathParam("id") Integer id) {
        return quoteService.getQuoteById(id);
    }

    @POST
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Quote addQuote(Quote quote) {
        return quoteService.addQuote(quote);
    }

    @PUT
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Quote updateQuote(Quote quote) {
        return quoteService.updateQuote(quote);
    }
}
