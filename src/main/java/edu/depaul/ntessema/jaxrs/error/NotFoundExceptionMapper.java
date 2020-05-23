package edu.depaul.ntessema.jaxrs.error;

import edu.depaul.ntessema.jaxrs.data.model.StatusMessage;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * This exception mapper is needed to send a 404 (not found)
 * response when the user types in a non-existent path in the application.
 */
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException nfe) {
        StatusMessage e = new StatusMessage(
                Response.Status.NOT_FOUND.getStatusCode(),
                "Endpoint not available.");
        return Response
                .status(Response.Status.NOT_FOUND)
                .header("Content-Type", "application/json")
                .entity(e)
                .build();
    }
}
