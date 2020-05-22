package edu.depaul.ntessema.jaxrs.exception;

import edu.depaul.ntessema.jaxrs.data.model.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnspecifiedExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception e) {
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Unspecified error"))
                .build();
    }
}
