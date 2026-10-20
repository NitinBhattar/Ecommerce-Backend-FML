package com.ecommercefml.apigateway.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

@Getter
@RequiredArgsConstructor
public class Endpoint {
    private final HttpMethod httpMethod;
    private final String path;
}
