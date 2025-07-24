package com.demetriusdemiurge.t1_homework_spring_security.services;

import com.demetriusdemiurge.t1_homework_spring_security.dto.AuthenticationResponse;
import com.demetriusdemiurge.t1_homework_spring_security.dto.LoginRequest;
import com.demetriusdemiurge.t1_homework_spring_security.dto.RefreshTokenRequest;
import com.demetriusdemiurge.t1_homework_spring_security.dto.RegisterRequest;
import com.demetriusdemiurge.t1_homework_spring_security.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final InMemoryUserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenBlacklistService tokenBlacklistService;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .login(request.getLogin())
                .email(request.getEmail())
                .password(request.getPassword())
                .roles(request.getRoles())
                .build();
        userDetailsService.save(user);

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword())
        );

        var user = userDetailsService.loadUserByUsername(request.getLogin());
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        String userLogin = jwtService.extractUsername(request.getRefreshToken());
        UserDetails userDetails = userDetailsService.loadUserByUsername(userLogin);

        if (jwtService.isTokenValid(request.getRefreshToken(), userDetails)) {
            var newAccessToken = jwtService.generateAccessToken(userDetails);
            return AuthenticationResponse.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(request.getRefreshToken())
                    .build();
        }
        throw new RuntimeException("Refresh token is not valid");
    }

    public void logout(String authHeader) {
        String token = authHeader.substring(7);
        long expiration = jwtService.getRemainingValidity(token);
        tokenBlacklistService.addToBlacklist(token, expiration);
    }

}