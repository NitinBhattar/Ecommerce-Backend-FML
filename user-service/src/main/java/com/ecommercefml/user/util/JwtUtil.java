package com.ecommercefml.user.util;

import com.ecommercefml.user.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKey key;
    private final long jwtAccessExpiration;
    private final long jwtRefreshExpiration;
    private final JwtParser jwtParser;

    public enum TokenType {
        ACCESS,
        REFRESH
    }

    // ctor
    public JwtUtil(@Value("${app.jwt.secret}") String secret,
                   @Value("${app.jwt.access-token-expiration}") long jwtAccessExpiration,
                   @Value("${app.jwt.refresh-token-expiration}") long jwtRefreshExpiration) {
        // JWT
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.jwtAccessExpiration = jwtAccessExpiration;
        this.jwtRefreshExpiration = jwtRefreshExpiration;

        // JWT Parser and Verifier
        this.jwtParser = Jwts.parser().verifyWith(key).build();
    }

    // Access token generation
    public String generateAccessToken(Long userId, String email, Role role) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                // subject is a standard claim
                .subject(String.valueOf(userId))
                .claim("email", email)
                .claim("role", role.name())
                .claim("type", TokenType.ACCESS.name())
                .issuedAt(new Date(now))
                .expiration(new Date(now + jwtAccessExpiration))
                // Choose algo by itself
                .signWith(key)
                // Used to serialize the JWT in String
                .compact();
    }

    // Refresh token generation
    public String generateRefreshToken(Long userId, String email, Role role) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("type", TokenType.REFRESH.name())
                .issuedAt(new Date(now))
                .expiration(new Date(now + jwtRefreshExpiration))
                .signWith(key)
                .compact();
    }

    // Validating token and token type
    private Claims validateToken(String token, TokenType tokenType) {
        // jwtParser will check, verify with key and build
        Claims claims = jwtParser.parseSignedClaims(token).getPayload();

        // Checking token type
        if (!tokenType.name().equals(claims.get("type", String.class))) {
            throw new IllegalArgumentException("Invalid " + tokenType.name() + " token");
        }

        return claims;
    }

    // Validating access token
    public Claims validateAccessToken(String accessToken) {
        return validateToken(accessToken, TokenType.ACCESS);
    }

    // Validating refresh token
    public Claims validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, TokenType.REFRESH);
    }

    // Extract userId
    public Long getUserIdFromToken(Claims claims) {
        String subject = claims.getSubject();

        if (subject == null || subject.isBlank()){
            throw new IllegalArgumentException("User ID missing from token");
        }

        return Long.valueOf(subject);
    }

    // Extract Role
    public Role getRoleFromToken(Claims claims) {
        String subject = claims.get("role", String.class);

        if (subject == null || subject.isBlank()){
            throw new IllegalArgumentException("Role missing from token");
        }

        return Role.valueOf(subject);
    }
}
