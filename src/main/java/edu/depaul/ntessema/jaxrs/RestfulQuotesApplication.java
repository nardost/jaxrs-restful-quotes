package edu.depaul.ntessema.jaxrs;

import org.glassfish.grizzly.http.server.HttpServer;

import java.io.IOException;

public class RestfulQuotesApplication {

    private static final String URI = "http://localhost:8888/";

    public static void main(String[] args) throws IOException {
        final HttpServer httpServer = GrizzlyHttpServer.run(URI);
        System.out.println(String.format("Webservice started at %s%nHit <ENTER> to stop server.", URI));
        System.in.read();
        httpServer.shutdown();
    }
}
