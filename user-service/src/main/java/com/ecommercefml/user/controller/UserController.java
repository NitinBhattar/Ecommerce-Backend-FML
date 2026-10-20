package com.ecommercefml.user.controller;

import com.ecommercefml.user.dto.LoginRequestDto;
import com.ecommercefml.user.dto.LoginResponseDto;
import com.ecommercefml.user.dto.UserCreateRequestDto;
import com.ecommercefml.user.dto.UserResponseDto;
import com.ecommercefml.user.service.impl.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserCreateRequestDto userCreateRequestDto) {
        UserResponseDto createdUser = userService.register(userCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loggedIn = userService.login(loginRequestDto);
        return ResponseEntity.ok(loggedIn);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        UserResponseDto userDetails = userService.getById(id);
        return ResponseEntity.ok(userDetails);
    }
}
