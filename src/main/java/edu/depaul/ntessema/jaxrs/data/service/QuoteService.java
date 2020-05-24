package edu.depaul.ntessema.jaxrs.data.service;

import edu.depaul.ntessema.jaxrs.data.model.StatusMessage;
import edu.depaul.ntessema.jaxrs.data.model.Quote;
import edu.depaul.ntessema.jaxrs.data.repository.Repository;
import edu.depaul.ntessema.jaxrs.data.repository.SimpleQuotesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static edu.depaul.ntessema.jaxrs.data.service.Utilities.*;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class QuoteService {
    /*
     * Use the interface instead of the implementation
     * to make future updates to the data access layer easy.
     */

    private final Repository<Quote, Integer> repository = SimpleQuotesRepository.getInstance();
    private final static Logger logger = LoggerFactory.getLogger(QuoteService.class);

    /**
     * HTTP Verb - GET
     *
     * Possible responses:
     *    200 (OK)
     *
     * If there are no paging parameters in the
     * request, return all quotes available.
     * This may not be practical as the number
     * of resources may be too large to handle.
     * I will, therefore, limit the number of
     * returned resources to an arbitrary number.
     */
    public List<Quote> getAllQuotes() {
        List<Quote> all = new ArrayList<>();
        /*
         * The number of quotes returned must be limited
         * in a practical service since all existing resources
         * may be too great in number to handle in a single HTTP response.
         *
         * //repository.findAll().forEach(all::add);
         */
        final int LIMIT = 8;
        int count = 0;
        for(Quote q : repository.findAll()) {
            all.add(q);
            count++;
            if(count == LIMIT) {
                break;
            }
        }
        logger.info(String.format("All quotes requested. Result set limited to %d quotes.", LIMIT));
        return all;
    }

    /**
     * HTTP Verb - GET
     *
     * Possible responses:
     *     200 (OK)
     *     400 (BAD REQUEST) - when paging params out of range.
     *
     * If paging parameters are in the query string,
     * return only the quotes that fall in the page.
     * Paging is based on quote ids sorted in ascending order.
     */
    public List<Quote> getQuotesPerPage(int page, int perPage) {
        List<Quote> quotesInPage = new ArrayList<>();
        List<Integer> allIds = new ArrayList<>();
        repository.getIds().forEach(allIds::add);
        repository.findAllById(getKeysInPage(page, perPage, allIds)).forEach(quotesInPage::add);
        logger.info(String.format("Page %s (%s quotes per page) requested.", page, perPage));
        return quotesInPage;
    }

    /**
     * HTTP Verb - GET
     *
     * Possible responses:
     *     200 (OK)
     *     404 (NOT FOUND)
     *
     * GET a specific quote by id. The user is
     * required to supply the id as a path parameter.
     *
     */
    public Quote getQuoteById(Integer id) {
        if(id == null) {
            final String message = "Null id value in request.";
            logger.error(message);
            throwBadRequestException(message);
        }
        Quote quote = repository.findById(id);
        /*
         * If a quote with the given id does not exist,
         * the data access layer return a null object,
         * in which case a 404 (NOT FOUND) response must be sent.
         */
        if(quote == null) {
            final String message = String.format("Quote with id %d was not found.", id);
            logger.error(message);
            throwNotFoundException(message);
        }
        logger.info(String.format("Quote with id %d requested.", id));
        return quote;
    }

    /**
     * HTTP Verb - POST
     *
     * Possible responses:
     *    200 (OK)
     *    400 (BAD REQUEST) - when an empty/null quote is POSTed
     *
     * Respond with a bad request response if
     * user POSTs a null quote or an empty quote.
     * Otherwise, save the quote with a new generated id.
     * A new id will be generated to the quote regardless
     * of the fact that it may already have an id.
     *
     * https://tools.ietf.org/html/rfc7231#section-4.3.3
     * https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods/POST
     *
     */
    public Quote addQuote(Quote quote) {
        /*
         * Do not allow a null or an empty quote to be saved.
         */
        if(quote == null || quote.getQuote() == null || quote.getQuote().equals("")) {
            final String message = "Quote cannot be null or empty.";
            logger.error(message);
            throwBadRequestException(message);
        }
        Quote q = repository.save(quote);
        logger.info(String.format("Quote with generated id %d POSTed.", q.getId()));
        return q;
    }

    /**
     * HTTP Verb - PUT
     *
     * Possible responses:
     *     204 (NO CONTENT)
     *     201 (CREATED)
     *
     * Return a 204 (No CONTENT) response if existing quote was updated.
     * Return a 201 (CREATED) response if quote was not in the list and is
     * saved as a new entry (created) by the operation.
     *
     * The content location is returned as a response header.
     *
     * https://tools.ietf.org/html/rfc7231#section-4.3.4
     */
    public Response updateQuote(final Quote quote) {
        if(quote == null || quote.getQuote() == null || quote.getQuote().equals("")) {
            final String message = "Quote cannot be null or empty.";
            logger.error(message);
            throwBadRequestException(message);
        }

        /*
         * Return the content location (id of updated quote) in response header.
         *
         * https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods/PUT
         *
         * The content location may be different from the id of the quote
         * provided in the method quote as explained in the repository method update().
         */
        final int contentLocation = repository.update(quote).getId();
        Response.Status status;

        if (quote.getId() == null || quote.getId() != contentLocation) {
            /*
             * If the incoming quote was not existent, a new id will have been
             * assigned it; in which case, the old and new ids are different.
             * This means that a quote is CREATED in PUT parlance.
             *
             * https://tools.ietf.org/html/rfc7231#section-4.3.4
             *
             * A 201 (CREATED) response should be sent to user.
             */
            status = Response.Status.CREATED;
        } else {
            /*
             * The old quote's state has been replaced by the new quote's state.
             *
             * A 200 or 204 response must be sent to user.
             * https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods/PUT
             *
             */
            status = Response.Status.NO_CONTENT;
        }
        /*
         * Construct the response and return it.
         */
        logger.info(String.format("Quote with id %d updated with status code %d.", contentLocation, status.getStatusCode()));
        return Response
                .status(status)
                .header("Content-Location", contentLocation)
                .build();
    }

    /**
     * HTTP Verb - DELETE
     *
     * Possible responses:
     *     200 (OK)
     *     404 (NOT FOUND)
     *
     * Return a 200 (OK) response if quote
     * with given id exists and is deleted.
     *
     * https://tools.ietf.org/html/rfc7231#section-4.3.5
     * https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods/DELETE
     *
     * Throw a NotFoundException if quote with id does not exist.
     */
    public Response deleteQuote(Integer id) {
        if(id == null) {
            final String message = "Id must be provided.";
            logger.error(message);
            throwBadRequestException(message);
        }

        /*
         * true if quote with id was there and was deleted.
         */
        boolean success = repository.delete(id);

        /*
         * If quote was not found, send a not found response.
         */
        if(!success) {
            final String message = String.format("Quote with id %d was not found.", id);
            logger.error(message);
            throwNotFoundException(message);
        }

        /*
         * If quote was deleted, send a 200 (OK)
         * response with content (status message).
         */
        final String message = String.format("Quote with id %d successfully deleted.", id);
        logger.info(message);
        return Response
                .status(Response.Status.OK)
                .entity(new StatusMessage(
                        Response.Status.OK.getStatusCode(),
                        message))
                .build();
    }
}
