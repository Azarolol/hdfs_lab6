package hdfs.lab4.azarolol;

import akka.actor.ActorRef;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;

import java.time.Duration;

import static akka.http.javadsl.server.Directives.*;

public class HTTPServer {
    private final Http http;
    private final ActorRef storage;
    private final 
    private static final String EMPTY_STRING = "";
    private static final String URL_QUERY = "url";
    private static final String COUNT_QUERY = "count";
    private static final String URL = "https://%s/?url=%s&count=%d";
    private static final Duration ASK_TIMEOUT = Duration.ofMillis(5000);

    public HTTPServer(Http http, ActorRef storage) {
        this.http = http;
        this.storage = storage;

    }

    public Route createRoute() {
        return route(
            path(EMPTY_STRING, () ->
                    route(
                            get(() ->
                                    parameter(URL_QUERY, (url) ->
                                            parameter(COUNT_QUERY, (count) -> {
                                                log.info(String.format(LOG_FORMAT_STRING, url, count))
                                        int count_int = Integer.parseInt(count);
                                        if (count_int == 0) {
                                            return completeWithFuture(
                                                    http.singleRequest(HttpRequest.create(url))
                                            );
                                        }
                                        return completeWithFuture(
                                                Patterns.ask(
                                                        storage,
                                                        new GetRandomServerMessage(),
                                                        ASK_TIMEOUT
                                                )
                                                        .thenCompose(
                                                                port ->
                                                                        http.singleRequest(HttpRequest.create(
                                                                                String.format(
                                                                                        URL,
                                                                                        port,
                                                                                        url,
                                                                                        count_int - 1
                                                                                )
                                                                        ))
                                                        )
                                        );
                                    }))
                    ))
        ));
    }
}
