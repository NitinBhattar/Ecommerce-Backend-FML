package com.ecommercefml.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;       
import org.springframework.web.server.ServerWebExchange;

@Slf4j
public class CustomRouteFilter extends AbstractGatewayFilterFactory<Object> {
    // ctor
    public CustomRouteFilter() {
        super(Object.class);
    }

    // Route filter, send requests to specific routes
    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerWebExchange mutatedExchange = exchange.mutate()
                    .request(
                            exchange.getRequest()
                                    .mutate()
                                    .header("X-Route-Header", "Added-By-Gateway")
                                    .build()
                    )
                    .build();

            return chain.filter(mutatedExchange).doFinally(signal -> {
                log.info("Request routed");
            });
        };
    }
}
