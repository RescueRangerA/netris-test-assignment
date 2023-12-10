package ru.netris.testassignment.aggregation.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;


public class WebClientLoggingFilter implements ExchangeFilterFunction {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebClientLoggingFilter.class);

    @Override
    public @NonNull Mono<ClientResponse> filter(@NonNull ClientRequest request, ExchangeFunction next) {
        logRequest(request);
        return next
                .exchange(request)
                .doOnNext(this::logResponse);
    }

    private void logRequest(ClientRequest request) {
        LOGGER.debug("Request: " + request.method() + " " + request.url());
    }

    private void logResponse(ClientResponse response) {
        HttpStatusCode status = response.statusCode();
        LOGGER.debug("Response: " + status + " (" + response.request().getURI() + ")");
    }
}
