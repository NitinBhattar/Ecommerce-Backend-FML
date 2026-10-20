package com.ecommercefml.user.controller;

import com.ecommercefml.user.dto.AccessTokenResponseDto;
import com.ecommercefml.user.service.impl.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/refreshAccessToken")
    public ResponseEntity<AccessTokenResponseDto> refreshAccessToken(@CookieValue("refreshToken") String refreshToken) {
        AccessTokenResponseDto newAccessToken = authService.refreshAccessToken(refreshToken);

        return ResponseEntity.ok(newAccessToken);
    }
}
