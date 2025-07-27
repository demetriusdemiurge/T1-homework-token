package com.demetriusdemiurge.t1_homework_token;

import com.demetriusdemiurge.t1_homework_token.dto.*;
import com.demetriusdemiurge.t1_homework_token.entities.*;
import com.demetriusdemiurge.t1_homework_token.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

//    private InMemoryUserDetailsService userDetailsService;
//    private JwtService jwtService;
//    private AuthenticationManager authenticationManager;
//    private TokenBlacklistService tokenBlacklistService;
//    private AuthService authService;
//
//    @BeforeEach
//    void setUp() {
//        userDetailsService = mock(InMemoryUserDetailsService.class);
//        jwtService = mock(JwtService.class);
//        authenticationManager = mock(AuthenticationManager.class);
//        tokenBlacklistService = mock(TokenBlacklistService.class);
//        authService = new AuthService(userDetailsService, jwtService, authenticationManager, tokenBlacklistService);
//    }
//
//    @Test
//    void register_shouldReturnTokens() {
//        RegisterRequest request = RegisterRequest.builder()
//                .login("new_admin")
//                .email("new_admin@example.com")
//                .password("new_admin123")
//                .roles(Set.of(Role.ADMIN))
//                .build();
//
//        when(jwtService.generateAccessToken(any())).thenReturn("access123");
//        when(jwtService.generateRefreshToken(any())).thenReturn("refresh123");
//
//        AuthenticationResponse response = authService.register(request);
//
//        verify(userDetailsService).save(any());
//        assertEquals("access123", response.getAccessToken());
//        assertEquals("refresh123", response.getRefreshToken());
//    }
//
//    @Test
//    void authenticate_shouldReturnTokens() {
//        LoginRequest login = LoginRequest.builder().login("new_admin").password("new_admin123").build();
//        UserDetails userDetails = mock(UserDetails.class);
//
//        when(userDetailsService.loadUserByUsername("new_admin")).thenReturn(userDetails);
//        when(jwtService.generateAccessToken(userDetails)).thenReturn("access-token");
//        when(jwtService.generateRefreshToken(userDetails)).thenReturn("refresh-token");
//
//        AuthenticationResponse response = authService.authenticate(login);
//
//        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
//        assertEquals("access-token", response.getAccessToken());
//        assertEquals("refresh-token", response.getRefreshToken());
//    }
//
//    @Test
//    void refreshToken_shouldReturnNewAccessToken() {
//        RefreshTokenRequest request = RefreshTokenRequest.builder().refreshToken("token123").build();
//        UserDetails user = mock(UserDetails.class);
//
//        when(jwtService.extractUsername("token123")).thenReturn("new_admin");
//        when(userDetailsService.loadUserByUsername("new_admin")).thenReturn(user);
//        when(jwtService.isTokenValid("token123", user)).thenReturn(true);
//        when(jwtService.generateAccessToken(user)).thenReturn("new-access-token");
//
//        AuthenticationResponse response = authService.refreshToken(request);
//        assertEquals("new-access-token", response.getAccessToken());
//        assertEquals("token123", response.getRefreshToken());
//    }
//
//    @Test
//    void refreshToken_shouldThrowExceptionOnInvalidToken() {
//        RefreshTokenRequest request = RefreshTokenRequest.builder().refreshToken("bad-token").build();
//        UserDetails user = mock(UserDetails.class);
//
//        when(jwtService.extractUsername("bad-token")).thenReturn("new_admin");
//        when(userDetailsService.loadUserByUsername("new_admin")).thenReturn(user);
//        when(jwtService.isTokenValid("bad-token", user)).thenReturn(false);
//
//        assertThrows(RuntimeException.class, () -> authService.refreshToken(request));
//    }
//
//    @Test
//    void logout_shouldAddTokenToBlacklist() {
//        String token = "Bearer abc.def.ghi";
//        when(jwtService.getRemainingValidity("abc.def.ghi")).thenReturn(15000L);
//
//        authService.logout(token);
//
//        verify(tokenBlacklistService).addToBlacklist("abc.def.ghi", 15000L);
//    }
}

