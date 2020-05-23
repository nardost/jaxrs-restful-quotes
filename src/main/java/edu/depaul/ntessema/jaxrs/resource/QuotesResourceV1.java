package edu.depaul.ntessema.jaxrs.resource;

import edu.depaul.ntessema.jaxrs.data.model.Quote;
import edu.depaul.ntessema.jaxrs.data.service.QuoteService;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

/*
 * The path is part of the contract and will not
 * change on upcoming versions.
 */
@Path("/quotes")
/*
 * API versioning through content negotiation (vendor media type).
 */
@Produces("application/vnd.api.v1+json")
public class QuotesResourceV1 {

    private final QuoteService quoteService = new QuoteService();
    /*
     * Default to be used if page is provided but per_page is missing.
     */
    private final String DEFAULT_PER_PAGE = "3";

    @GET
    public List<Quote> getAllQuotes(
            @QueryParam("page") Integer page,
            @DefaultValue(DEFAULT_PER_PAGE) @QueryParam("per_page") Integer perPage) {

        /*
         * If page is not provided, get all quotes.
         */
        if(page == null) {
            return quoteService.getAllQuotes();
        }

        return quoteService.getQuotesPerPage(page, perPage);
    }

    @GET
    @Path("/{id}")
    public Quote getQuote(@PathParam("id") Integer id) {
        return quoteService.getQuoteById(id);
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Quote addQuote(Quote quote) {
        return quoteService.addQuote(quote);
    }

    @PUT
    @Consumes(APPLICATION_JSON)
    public Quote updateQuote(Quote quote) {
        return quoteService.updateQuote(quote);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteQuote(@PathParam("id") Integer id) {
        return quoteService.deleteQuote(id);
    }
}
