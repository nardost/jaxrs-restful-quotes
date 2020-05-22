package edu.depaul.ntessema.jaxrs.exception;

import edu.depaul.ntessema.jaxrs.data.model.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException qse) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(new ErrorMessage(Response.Status.NOT_FOUND.getStatusCode(), qse.getMessage()))
                .build();
    }
}
