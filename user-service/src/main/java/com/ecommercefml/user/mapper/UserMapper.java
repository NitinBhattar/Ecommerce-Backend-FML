package com.ecommercefml.user.mapper;

import com.ecommercefml.user.dto.UserCreateRequestDto;
import com.ecommercefml.user.dto.UserResponseDto;
import com.ecommercefml.user.entity.User;
import com.ecommercefml.user.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public User toUserEntity(UserCreateRequestDto userCreateRequestDto) {
        return User.builder()
                .name(userCreateRequestDto.getName())
                .email(userCreateRequestDto.getEmail())
                .password(passwordEncoder.encode(userCreateRequestDto.getPassword()))
                .phone(userCreateRequestDto.getPhone())
                .role(Role.USER)
                .build();
    }

    public UserResponseDto toUserResponse(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
