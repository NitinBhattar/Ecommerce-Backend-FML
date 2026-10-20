package com.ecommercefml.user.service.impl;

import com.ecommercefml.user.dto.AccessTokenResponseDto;

public interface AuthService {
    AccessTokenResponseDto refreshAccessToken(String refreshToken);
}
