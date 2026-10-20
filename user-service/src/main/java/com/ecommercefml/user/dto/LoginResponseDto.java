package com.ecommercefml.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class LoginResponseDto {
    private final Long userId;
    private final String email;
    private final String name;
    private final String accessToken;
    private final String refreshToken;
    private final String tokenType = "Bearer";

    // Not to pass "Bearer" everytime
    public LoginResponseDto(String accessToken, String refreshToken, Long userId, String email, String name) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.email = email;
        this.name = name;
    }
}
