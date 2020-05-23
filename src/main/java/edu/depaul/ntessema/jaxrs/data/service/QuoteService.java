package edu.depaul.ntessema.jaxrs.data.service;

import edu.depaul.ntessema.jaxrs.data.model.StatusMessage;
import edu.depaul.ntessema.jaxrs.data.model.Quote;
import edu.depaul.ntessema.jaxrs.data.repository.Repository;
import edu.depaul.ntessema.jaxrs.data.repository.SimpleQuotesRepository;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuoteService {

    private final Repository<Quote, Integer> repository = SimpleQuotesRepository.getInstance();

    public List<Quote> getAllQuotes() {
        List<Quote> all = new ArrayList<>();
        repository.findAll().forEach(all::add);
        return all;
    }

    public List<Quote> getQuotesPerPage(int page, int perPage) {
        List<Quote> quotesInPage = new ArrayList<>();
        repository.findAllById(getKeysInPage(page, perPage)).forEach(quotesInPage::add);
        return quotesInPage;
    }

    public Quote getQuoteById(Integer id) {
        if(id == null) {
            throwBadRequestException("Id must be provided");
        }
        Quote quote = repository.findById(id);
        if(quote == null) {
            throwNotFoundException("Quote with id " + id + " was not found.");
        }
        return quote;
    }

    public Quote addQuote(Quote quote) {
        if(quote == null || quote.getQuote() == null || quote.getQuote().equals("")) {
            throwBadRequestException("Quote cannot be null or empty.");
        }
        return repository.save(quote);
    }

    public Quote updateQuote(final Quote quote) {
        if(quote == null || quote.getQuote() == null || quote.getQuote().equals("")) {
            throwBadRequestException("Quote cannot be null or empty.");
        }
        final Quote oldQuote = repository.update(quote);
        if (oldQuote == null) {
            /*
             * TODO
             *  If it does not exist, what should we do? CREATE it?
             *  (Option 1)  Generate a new id and save it.
             *              To preserve the id scheme, we should generate
             *              a new id instead of taking whatever is given.
             *  (Option 2) Return a quote not found response.
             */
            throwNotFoundException("Quote with id " + quote.getId() + " was not found.");
        }
        return oldQuote;
    }

    public Response deleteQuote(Integer id) {
        if(id == null) {
            throwBadRequestException("Id must be provided.");
        }

        /*
         * true if quote with id was there and was deleted.
         */
        boolean success = repository.delete(id);

        /*
         * If quote was not found, send a not found response.
         */
        if(!success) {
            throwNotFoundException("Quote with id " + id + " was not found.");
        }

        /*
         * If quote was deleted, send a success response.
         */
        return Response
                .status(Response.Status.OK)
                .entity(new StatusMessage(
                        Response.Status.OK.getStatusCode(),
                        "Quote with id " + id + " successfully deleted"))
                .build();
    }

    /**
     * Method that returns a list of ids that
     * fall in the given page.
     *
     * This method is factored out of the getQuotesPerPage() method.
     *
     * @param page
     * @param perPage
     * @return - a list of ids
     */
    private List<Integer> getKeysInPage(int page, int perPage) {
        List<Integer> allKeys = new ArrayList<>();
        repository.getIds().forEach(allKeys::add);
        Collections.sort(allKeys);

        final int TOTAL_NUMBER_OF_QUOTES = allKeys.size();
        final int QUOTIENT = TOTAL_NUMBER_OF_QUOTES / perPage;
        final int REMAINDER = TOTAL_NUMBER_OF_QUOTES % perPage;
        final int TOTAL_NUMBER_OF_PAGES = QUOTIENT + ((REMAINDER > 0) ? 1 : 0);

        if(page > TOTAL_NUMBER_OF_PAGES || page < 1 || perPage > TOTAL_NUMBER_OF_QUOTES) {
            throwBadRequestException("Bad paging parameters.");
        }

        final int FIRST = (page - 1) * perPage;
        int delta = perPage - 1;
        if(page == TOTAL_NUMBER_OF_PAGES && REMAINDER > 0) {
            delta = REMAINDER - 1;
        }
        final int LAST = FIRST + delta;
        List<Integer> keysInPage = new ArrayList<>();
        for(int i = FIRST; i <= LAST; i++) {
            keysInPage.add(allKeys.get(i));
        }
        return keysInPage;
    }

    /**
     * Repetitive code for throwing exception
     * factored out as private method.
     * @param message - the message in the exception
     */
    private void throwNotFoundException(String message) {
        StatusMessage e = new StatusMessage(
                Response.Status.NOT_FOUND.getStatusCode(),
                message);
        Response response = Response
                .status(Response.Status.NOT_FOUND)
                .entity(e)
                .build();
        throw new NotFoundException(response);
    }

    /**
     * Repetitive code for throwing exception
     * factored out as private method.
     * @param message - the message in the exception
     */
    private void throwBadRequestException(String message) {
        StatusMessage e = new StatusMessage(
                Response.Status.BAD_REQUEST.getStatusCode(),
                message);
        Response response = Response
                .status(Response.Status.BAD_REQUEST)
                .entity(e)
                .build();
        throw new BadRequestException(response);
    }
}
