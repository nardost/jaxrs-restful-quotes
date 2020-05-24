package edu.depaul.ntessema.jaxrs.data.service;

import edu.depaul.ntessema.jaxrs.data.model.StatusMessage;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author nardos
 *
 * Utility methods that are factored
 * out of the service class.
 */

public class Utilities {

    /*
     * In case all resources are requested, limit the
     * number returned to this number.
     */
    public static final int LIMIT = 8;

    /**
     * Method that returns a list of ids that fall in the given page.
     *
     * Paging is based on ids sorted in ascending order.
     */
    public static List<Integer> getKeysInPage(int page, int perPage, List<Integer> allIds) {

        /*
         * Quotes should be sorted by their ids
         * in order to get the paging right.
         */
        Collections.sort(allIds);
        /*
         * The quote ids that fall in page.
         */
        List<Integer> keysInPage = new ArrayList<>();

        /*
         * (1) Get total number of quotes in DB.
         * (2) Get total number of pages based on perPage.
         */

        final int TOTAL_NUMBER_OF_QUOTES = allIds.size();
        final int QUOTIENT = TOTAL_NUMBER_OF_QUOTES / perPage;
        final int REMAINDER = TOTAL_NUMBER_OF_QUOTES % perPage;
        final int TOTAL_NUMBER_OF_PAGES = QUOTIENT + ((REMAINDER > 0) ? 1 : 0);

        /*
         * If the provided paging parameters fall
         * out of range, send a "bad request" response.
         */
        if(page > TOTAL_NUMBER_OF_PAGES || page < 1) {
            throw newBadRequestException("Bad paging parameters.");
        }
        /*
         * if per_page exceeds the number of quotes available,
         * try to return all quotes but never exceed the limit set.
         */
        if(page == 1 && perPage > TOTAL_NUMBER_OF_QUOTES) {
            for(int i = 0; i < allIds.size() && i < LIMIT; i++) {
                keysInPage.add(allIds.get(i));
            }
            return keysInPage;
        }

        /*
         * Get the first and the last indices of
         * the quotes ids that fall in the page.
         */
        final int FIRST = (page - 1) * perPage;
        int delta = perPage - 1;
        if(page == TOTAL_NUMBER_OF_PAGES && REMAINDER > 0) {
            delta = REMAINDER - 1;
        }
        final int LAST = FIRST + delta;
        for(int i = FIRST; i <= LAST; i++) {
            keysInPage.add(allIds.get(i));
        }
        return keysInPage;
    }

    /**
     * Factored out repetitive code for throwing exceptions.
     *
     * @param message - the message in the exception
     */
    public static NotFoundException newNotFoundException(String message) {
        StatusMessage e = new StatusMessage(
                Response.Status.NOT_FOUND.getStatusCode(),
                message);
        Response response = Response
                .status(Response.Status.NOT_FOUND)
                .entity(e)
                .build();
        return new NotFoundException(response);
    }

    /**
     * Factored out repetitive code for throwing exceptions.
     *
     * @param message - the message in the exception
     */
    public static BadRequestException newBadRequestException(String message) {
        StatusMessage e = new StatusMessage(
                Response.Status.BAD_REQUEST.getStatusCode(),
                message);
        Response response = Response
                .status(Response.Status.BAD_REQUEST)
                .entity(e)
                .build();
        return new BadRequestException(response);
    }
}
