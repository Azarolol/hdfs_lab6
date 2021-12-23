package hdfs.lab4.azarolol;

import akka.http.javadsl.Http;
import akka.http.javadsl.server.Route;

import static akka.http.javadsl.server.Directives.route;

public class HTTPServer {
    private final Http http;

    public HTTPServer(Http http) {
        this.http = http;
    }

    public Route createRoute() {
        return route(

        )
    }
}
