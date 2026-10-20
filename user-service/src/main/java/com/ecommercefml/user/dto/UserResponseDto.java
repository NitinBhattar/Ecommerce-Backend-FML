package com.ecommercefml.user.dto;

import com.ecommercefml.user.enums.Role;
import lombok.*;

import java.time.Instant;

// Builder is used for optional fields
@Data
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Role role;
    private Instant createdAt;
    private Instant updatedAt;
}
