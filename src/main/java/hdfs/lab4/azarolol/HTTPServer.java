package hdfs.lab4.azarolol;

import akka.http.javadsl.Http;
import akka.http.javadsl.server.Route;

public class HTTPServer {
    private final Http http;

    public HTTPServer(Http http) {
        this.http = http;
    }

    public Route createRoute() {
        
    }
}
