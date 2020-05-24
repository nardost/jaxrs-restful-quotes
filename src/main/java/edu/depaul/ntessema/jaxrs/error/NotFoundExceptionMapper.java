package edu.depaul.ntessema.jaxrs.error;

import edu.depaul.ntessema.jaxrs.data.model.StatusMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * This exception mapper is needed to send a 404 (not found)
 * response when the user types in a non-existent endpoint in the application.
 */
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException nfe) {
        final String message = "Endpoint not available.";
        Logger logger = LoggerFactory.getLogger(NotFoundExceptionMapper.class);
        logger.error(message);
        StatusMessage e = new StatusMessage(
                Response.Status.NOT_FOUND.getStatusCode(),
                message);
        return Response
                .status(Response.Status.NOT_FOUND)
                .header("Content-Type", "application/json")
                .entity(e)
                .build();
    }
}
