package com.ecommercefml.user.mapper;

import com.ecommercefml.user.dto.LoginResponseDto;
import com.ecommercefml.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
    public LoginResponseDto toLoginResponse(User user, String accessToken, String refreshToken) {
        return new LoginResponseDto(accessToken, refreshToken, user.getId(), user.getEmail(), user.getName());
    }
}
