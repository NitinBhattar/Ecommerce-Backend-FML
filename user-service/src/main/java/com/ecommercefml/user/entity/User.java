package com.ecommercefml.user.entity;

import com.ecommercefml.user.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    // GenerationType.IDENTITY is auto increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // EnumType.STRING means enum as string not indexes
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String phone;

    private Instant createdAt;
    private Instant updatedAt;

    // Before creating an object, it changes value
    @PrePersist
    public void onCreate() {
        createdAt = Instant.now();
        updatedAt = createdAt;
    }
    // Before updating an object, it changes value
    @PreUpdate
    public void onUpdate() {
        updatedAt = Instant.now();
    }
}
