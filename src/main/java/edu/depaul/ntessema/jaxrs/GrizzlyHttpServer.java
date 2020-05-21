package edu.depaul.ntessema.jaxrs;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class GrizzlyHttpServer {

    public static HttpServer run(final String baseURI) {
        final ResourceConfig resourceConfig = new ResourceConfig().packages("edu.depaul.ntessema.jaxrs");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(baseURI), resourceConfig);
    }
}
