package edu.depaul.ntessema.jaxrs;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * @author nardos
 *
 * Start an HTTP server and launch the
 * web service application at the given URI.
 */
public class RestfulQuotesApplication {

    private static final String URI = "http://localhost:8888/";

    public static void main(String[] args) throws IOException {
        /*
         * Show some ASCII logo.
         */
        displayLogo(URI);
        /*
         * Start an HTTP server process that listens for
         * requests at the host address and port given in the URI.
         */
        final HttpServer httpServer = GrizzlyHttpServer.run(URI);
        System.out.println("Hit <ENTER> to stop server.");
        /*
         * Record when the server was started.
         */
        final String timestamp = Timestamp.from(Instant.now()).toString();
        System.out.println(String.format("[%s] Webservice started at %s", timestamp, URI));
        /*
         * Convenient way to stop server. Just hit enter.
         */
        System.in.read();
        httpServer.shutdown();
    }

    /**
     * Print an ASCII logo.
     */
    public static void displayLogo(final String s) {
        final String FORMAT = "%15s%s\n";
        final int WIDTH = 45;
        final int L = (WIDTH - s.length()) / 2;
        final int R = WIDTH - s.length() - L - 2;
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(FORMAT, "", "+-------------------------------------------+"));
        sb.append(String.format(FORMAT, "", "|               Quote of the Day            |"));
        sb.append(String.format(FORMAT, "", "|   (A JAX-RS Based RESTful Web Service)    |"));
        sb.append(String.format(FORMAT, "", "|                Nardos Tessema             |"));
        sb.append(String.format(FORMAT, "", "+-------------------------------------------+"));
        sb.append(String.format("%15s%s%s%s%s%s\n", "", "|", spaces(L), s, spaces(R), "|"));
        sb.append(String.format(FORMAT, "", "+-------------------------------------------+"));
        System.out.println(sb.toString());
    }
    /**
     * Whitespaces to format the ASCII logo.
     */
    private static String spaces(final int n) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < n; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }
}

/**
 * A class that starts an HTTP server process.
 */
class GrizzlyHttpServer {

    public static HttpServer run(final String baseURI) {
        final ResourceConfig resourceConfig = new ResourceConfig().packages("edu.depaul.ntessema.jaxrs");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(baseURI), resourceConfig);
    }
}