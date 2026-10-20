package com.ecommercefml.user.service.impl;

import com.ecommercefml.user.dto.AccessTokenResponseDto;
import com.ecommercefml.user.entity.User;
import com.ecommercefml.user.exception.ResourceNotFoundException;
import com.ecommercefml.user.repository.UserRepository;
import com.ecommercefml.user.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public AccessTokenResponseDto refreshAccessToken(String refreshToken) {
        // Validate refresh token
        Claims claims = jwtUtil.validateRefreshToken(refreshToken);
        // Extract user id
        Long userId = jwtUtil.getUserIdFromToken(claims);

        // Fetch user from repo
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        // Generate new access token
        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getEmail(), user.getRole());

        return new AccessTokenResponseDto(accessToken);
    }
}
