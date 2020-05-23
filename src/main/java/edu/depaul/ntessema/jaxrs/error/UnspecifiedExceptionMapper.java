package edu.depaul.ntessema.jaxrs.error;

import edu.depaul.ntessema.jaxrs.data.model.StatusMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * This exception mapper is needed to send a generic
 * error response when some unspecified exception is thrown.
 */
@Provider
public class UnspecifiedExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable throwable) {
        StatusMessage e = new StatusMessage(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                "Unspecified error occurred");
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .header("Content-Type", "application/json")
                .entity(e)
                .build();
    }
}
