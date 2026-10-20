package com.ecommercefml.user.service.impl;

import com.ecommercefml.user.dto.LoginRequestDto;
import com.ecommercefml.user.dto.LoginResponseDto;
import com.ecommercefml.user.dto.UserCreateRequestDto;
import com.ecommercefml.user.dto.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserCreateRequestDto userCreateRequestDto);

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    UserResponseDto getById(Long Id);
}
