package edu.depaul.ntessema.jaxrs.error;

import edu.depaul.ntessema.jaxrs.data.model.StatusMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author nardos
 *
 * This exception mapper will catch client errors
 * that were not explicitly caught in the code.
 *
 * Example:
 *      DELETE request without the id parameter in the path.
 */
@Provider
public class ClientErrorExceptionMapper implements ExceptionMapper<ClientErrorException> {

    @Override
    public Response toResponse(ClientErrorException bre) {
        final String message = "Unspecified client error. Check your request.";
        Logger logger = LoggerFactory.getLogger(ClientErrorExceptionMapper.class);
        logger.error(message);
        StatusMessage e = new StatusMessage(
                Response.Status.BAD_REQUEST.getStatusCode(),
                message);
        return Response
                .status(Response.Status.BAD_REQUEST)
                .header("Content-Type", "application/json")
                .entity(e)
                .build();
    }
}
