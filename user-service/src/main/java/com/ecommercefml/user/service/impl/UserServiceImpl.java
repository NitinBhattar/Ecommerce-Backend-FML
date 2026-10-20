package com.ecommercefml.user.service.impl;

import com.ecommercefml.user.dto.LoginRequestDto;
import com.ecommercefml.user.dto.LoginResponseDto;
import com.ecommercefml.user.dto.UserCreateRequestDto;
import com.ecommercefml.user.dto.UserResponseDto;
import com.ecommercefml.user.entity.User;
import com.ecommercefml.user.exception.InvalidCredentialsException;
import com.ecommercefml.user.exception.ResourceAlreadyExistsException;
import com.ecommercefml.user.exception.ResourceNotFoundException;
import com.ecommercefml.user.mapper.AuthMapper;
import com.ecommercefml.user.mapper.UserMapper;
import com.ecommercefml.user.repository.UserRepository;
import com.ecommercefml.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    // No need for @Autowired, we made req args ctor and final
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final AuthMapper authMapper;

    @Override
    public UserResponseDto register(UserCreateRequestDto userCreateRequestDto) {
        // Checking if user already exist or using the same email
        if (userRepository.existsByEmail(userCreateRequestDto.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        // Saving user
        User user = userMapper.toUserEntity(userCreateRequestDto);
        User savedUser = userRepository.save(user);

        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        // Fetching user from repo
        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        // Password matching
        if ( !passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword()) ) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        // Generating tokens
        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getEmail(), user.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getEmail(), user.getRole());

        return authMapper.toLoginResponse(user, accessToken, refreshToken);
    }

    @Override
    @PreAuthorize("@userSecurity.isOwnerOrAdmin(#id, authentication)")
    public UserResponseDto getById(Long id) {
        // Fetching user from repo
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.toUserResponse(user);
    }
}
