package edu.depaul.ntessema.jaxrs.exception;

import edu.depaul.ntessema.jaxrs.data.model.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {
    @Override
    public Response toResponse(BadRequestException bre) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new ErrorMessage(Response.Status.BAD_REQUEST.getStatusCode(), bre.getMessage()))
                .build();
    }
}
