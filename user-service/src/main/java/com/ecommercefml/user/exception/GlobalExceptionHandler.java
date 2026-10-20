package com.ecommercefml.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Resource already exists
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleResourceAlreadyExists(ResourceAlreadyExistsException ex) {
        Map<String, Object> mp = Map.of("error", "Conflict", "message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(mp);
    }

    // Resource not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, Object> mp = Map.of("error", "Not Found", "message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mp);
    }

    // Method argument for @Valid tag in requests
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();

        // Putting all errors and there messages in errors map
        ex.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        Map<String, Object> mp = Map.of("error", "Validation Failed", "message", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mp);
    }

    // Invalid credentials
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidCredentials(InvalidCredentialsException ex) {
        Map<String, Object> mp = Map.of("error", "Unauthorized", "message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mp);
    }

    // If non methods handles it, this will handle it
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAll(Exception ex) {
        Map<String, Object> mp = Map.of("error", "Internal Server Error", "message", "Something went wrong");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mp);
    }
}
