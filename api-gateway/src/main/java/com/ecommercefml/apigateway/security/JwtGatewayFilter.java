package com.ecommercefml.apigateway.security;

import com.ecommercefml.apigateway.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.util.PathMatcher;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtGatewayFilter implements GatewayFilter, Ordered {
    private final JwtUtil jwtUtil;
    private PathMatcher pathMatcher = new AntPathMatcher();

    // No verification of JWT
    private static final List<Endpoint> PUBLIC_URLS = List.of(
            new Endpoint(HttpMethod.POST, "/api/users/register"),
            new Endpoint(HttpMethod.POST, "/api/users/login"),
            new Endpoint(HttpMethod.GET, "/api/products"),
            new Endpoint(HttpMethod.GET, "/api/products/**")
    );

    // Role -> HttpMethod -> path
    private static final Map<Role, Map<HttpMethod, List<String>>> ROLE_API_PERMISSIONS = Map.of(
            Role.ADMIN, Map.of(HttpMethod.GET, List.of("/api/products/**"),
                    HttpMethod.PUT, List.of("/api/products/**"),
                    HttpMethod.POST, List.of("/api/products/**"),
                    HttpMethod.DELETE, List.of("/api/products/**")),
            Role.SELLER, Map.of(HttpMethod.GET, List.of("/api/products/**"),
                    HttpMethod.PUT, List.of("/api/products/**"),
                    HttpMethod.POST, List.of("/api/products/**"),
                    HttpMethod.DELETE, List.of("/api/products/**")),
            Role.USER, Map.of(HttpMethod.GET, List.of("/api/products/**"))
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final HttpMethod method = exchange.getRequest().getMethod();
        final String path = exchange.getRequest().getURI().getPath();

        // Bypassing public URLs to next filter
        if (PUBLIC_URLS.stream().anyMatch(endpoint -> endpoint.getHttpMethod() == method && pathMatcher.match(endpoint.getPath(), path))) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // Auth header check
        if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }

        final String token = authHeader.substring(7);
        final Claims claims;
        final String userId;
        final Role role;

        try {
            claims = jwtUtil.validateAccessToken(token);
            userId = jwtUtil.getUserIdFromToken(claims).toString();
            role = jwtUtil.getRoleFromToken(claims);
        } catch (JwtException | IllegalArgumentException ex) {
            return unauthorized(exchange);
        }

        // Role based authenticationPP
        if (!isAuthorized(role, method, path)) {
            return forbidden(exchange);
        }

        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().header("X-User-Id", userId).header("X-User-Role", role.name()).build();

        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    // Unauthorized request
    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    // Role based authorization
    private boolean isAuthorized(Role role, HttpMethod httpMethod, String path) {
        Map<HttpMethod, List<String>> permissions = ROLE_API_PERMISSIONS.get(role);

        // Skipping NullPointerException
        if (permissions == null) {
            return false;
        }

        List<String> allowedPaths = permissions.get(httpMethod);

        // Skipping NullPointerException
        if (allowedPaths == null) {
            return false;
        }

        return allowedPaths.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    // Forbidden access
    private Mono<Void> forbidden(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }

    // This is priority of this filter, lower the number is higher the priority is
    @Override
    public int getOrder() {
        return -1;
    }
}
