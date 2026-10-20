package com.ecommercefml.user.util;

import com.ecommercefml.user.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

// @Component tells Spring to create an instance of this class and put it in the Spring ApplicationContext
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        // Check for authHeader, something that requires user to be logged in, only check in that process, else continue security filter chain
        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            final String token = authHeader.substring(7);

            try {
                final Claims claims = jwtUtil.validateAccessToken(token);
                final Long userId = jwtUtil.getUserIdFromToken(claims);
                final Role role = jwtUtil.getRoleFromToken(claims);

                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

                // Principal (who is making the request), credentials (no password as JWT has been validated), Authority list (People allowed authority to Spring Security)
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, List.of(authority));

                // Set context only if user isn't authenticated for this http request, don't overwrite it again
                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Whole spring will know that this user is logged in
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JwtException | IllegalArgumentException ex) {
                log.debug("JWT authentication failed: {}", ex.getMessage());
                SecurityContextHolder.clearContext();
                throw ex;
            }
        }

        // Continues filterChain
        filterChain.doFilter(request, response);
    }
}
