package com.ecommercefml.user.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("userSecurity")
public class UserSecurity {
    public boolean isOwnerOrAdmin(Long userId, Authentication authentication) {
        Long authenticatedUserId = (Long) authentication.getPrincipal();

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(
                        a -> a.getAuthority().equals("ROLE_ADMIN")
                );

        return isAdmin || (authenticatedUserId != null && authenticatedUserId.equals(userId));
    }
}
