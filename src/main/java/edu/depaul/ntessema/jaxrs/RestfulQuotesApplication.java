package edu.depaul.ntessema.jaxrs;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * @author nardos
 *
 * Start an HTTP server and launch the
 * web service application at the given URI.
 */
public class RestfulQuotesApplication {

    private static final String URI = "http://localhost:8888/";

    public static void main(String[] args) throws IOException {
        final HttpServer httpServer = GrizzlyHttpServer.run(URI);
        System.out.println(String.format("Webservice started at %s%nHit <ENTER> to stop server.", URI));
        System.in.read();
        httpServer.shutdown();
    }
}

class GrizzlyHttpServer {

    public static HttpServer run(final String baseURI) {
        final ResourceConfig resourceConfig = new ResourceConfig().packages("edu.depaul.ntessema.jaxrs");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(baseURI), resourceConfig);
    }
}