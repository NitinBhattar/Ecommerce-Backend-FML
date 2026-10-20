package com.ecommercefml.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccessTokenResponseDto {
    private final String accessToken;
}
