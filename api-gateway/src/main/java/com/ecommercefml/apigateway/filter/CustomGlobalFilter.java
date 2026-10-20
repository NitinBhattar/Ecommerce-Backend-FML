package com.ecommercefml.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

// Intercept request and response, everything passes through this global filter
@Slf4j
public class CustomGlobalFilter implements GlobalFilter {

    // WebFlux is for reactive / non-blocking web applications
    // Reactive programming means, not ot let the thread sit idle, but make it to do other, basically make it asynchronous / non-blocking
    // Mono is a class which helps in reactive apps, will give you result async-ly
    // i.e. thread in the meantime completes other tasks which doesn't involve, response from prev task
    // We don't use it more often because JPA is traditional synchronous / blocking.
    // So Mono is 0 or 1, where 0 is nothing and 1 is T (template)
    // Similarly, Flux is 0 to N, where 0 is nothing and 1 to N are no. of articles,  e.g.: multiple users
    // User, list<User> (Non-reactive) -> Mono<User>, Flux<User> (Reactive)

    // Gateway has to be reactive for it to handle multiple requests at once
    // We use Mono<void> here because we just want to make it async, requests has to be processed by each service only, gateway juts directs it

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String httpMethod = exchange.getRequest().getMethod().name();
        // Get path will only show path, but getURI shows all query parameters
        String path = exchange.getRequest().getURI().getPath();
        long startTime = System.currentTimeMillis();

        log.info("Request intercepted: {} {}", httpMethod, path);

        // Reactor responds to signal
        return chain.filter(exchange).doFinally(signal -> {
            long duration = System.currentTimeMillis() - startTime;
            HttpStatusCode httpStatusCode = exchange.getResponse().getStatusCode();

            log.info("Request completed: {} {} -> {} ({} ms)", httpMethod, path, (httpStatusCode != null) ? httpStatusCode.value() : "UNKNOWN", duration);
        });
    }
}
