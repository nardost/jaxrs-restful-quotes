package edu.depaul.ntessema.jaxrs.resource;

import edu.depaul.ntessema.jaxrs.data.model.Quote;
import edu.depaul.ntessema.jaxrs.data.service.QuoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

/**
 * @author nardos
 *
 * API Version 1
 */

/*
 * The path is part of the contract and
 * will not change on upcoming versions.
 */
@Path("/quotes")
/*
 * API versioning through content negotiation (vendor media type).
 */
@Produces({ "application/json", "application/vnd.api.v1+json" })
public class QuotesResourceV1 {

    private final QuoteService quoteService = new QuoteService();
    private static final Logger logger = LoggerFactory.getLogger(QuotesResourceV1.class);
    /*
     * Default to be used if page is provided but per_page is missing.
     */
    private final String DEFAULT_PER_PAGE = "3";
    /*
     * Just to show which API version is invoked.
     */
    private final String API_VERSION_INVOKED = "API Version 1 employed.";

    /*
     * GET a collection of quotes.
     *
     * If paging parameters provided, return the quotes that fall in the page.
     * Otherwise return all quotes.
     *
     * In this web service, the number of quotes returned in paging parameters
     * are not provided is limited to some arbitrary number because the number of
     * available quotes may be too large for a single HTTP response.
     *
     * See service method for details.
     */
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
        logger.info(API_VERSION_INVOKED);
        return quoteService.getQuotesPerPage(page, perPage);
    }

    /*
     * GET a specific quote by id.
     *
     * See service method for details.
     */
    @GET
    @Path("/{id}")
    public Quote getQuote(@PathParam("id") Integer id) {
        logger.info(API_VERSION_INVOKED);
        return quoteService.getQuoteById(id);
    }

    /*
     * POST a quote. Request payload is in JSON format.
     * Return the POSTed quote in the response body.
     *
     * See service method for details.
     */
    @POST
    @Consumes(APPLICATION_JSON)
    public Quote addQuote(Quote quote) {
        logger.info(API_VERSION_INVOKED);
        return quoteService.addQuote(quote);
    }

    /*
     * PUT a quote. Request payload is in JSON format.
     *
     * See service method for details.
     */
    @PUT
    @Consumes(APPLICATION_JSON)
    public Response updateQuote(Quote quote) {
        logger.info(API_VERSION_INVOKED);
        return quoteService.updateQuote(quote);
    }

    /*
     * DELETE a quote identified by id.
     *
     * See service method for details.
     */
    @DELETE
    @Path("/{id}")
    public Response deleteQuote(@PathParam("id") Integer id) {
        logger.info(API_VERSION_INVOKED);
        return quoteService.deleteQuote(id);
    }
}
