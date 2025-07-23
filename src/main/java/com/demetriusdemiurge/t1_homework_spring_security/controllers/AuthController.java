package com.demetriusdemiurge.t1_homework_spring_security.controllers;

import com.demetriusdemiurge.t1_homework_spring_security.dto.AuthenticationResponse;
import com.demetriusdemiurge.t1_homework_spring_security.dto.LoginRequest;
import com.demetriusdemiurge.t1_homework_spring_security.dto.RefreshTokenRequest;
import com.demetriusdemiurge.t1_homework_spring_security.dto.RegisterRequest;
import com.demetriusdemiurge.t1_homework_spring_security.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        authService.logout(authHeader);
        return ResponseEntity.ok("User logged out successfully.");
    }
}
