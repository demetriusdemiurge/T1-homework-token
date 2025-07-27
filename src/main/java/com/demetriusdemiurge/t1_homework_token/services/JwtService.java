package com.demetriusdemiurge.t1_homework_token.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration.access}")
    private long accessTokenExpiration;
    @Value("${jwt.expiration.refresh}")
    private long refreshTokenExpiration;
    private final JweService jweService;

    public String generateAccessToken(UserDetails userDetails) {
        String jwt = generateToken(new HashMap<>(), userDetails, accessTokenExpiration);
        try {
            return jweService.encrypt(jwt);
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt access token", e);
        }
    }

    public String generateRefreshToken(UserDetails userDetails) {
        String jwt = generateToken(new HashMap<>(), userDetails, refreshTokenExpiration);
        try {
            return jweService.encrypt(jwt);
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt refresh token", e);
        }
    }

    public String extractUsername(String token) {
        try {
            String jwt = jweService.decrypt(token);
            return extractClaim(jwt, Claims::getSubject);
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt or parse token", e);
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            String jwt = jweService.decrypt(token);
            final String username = extractClaim(jwt, Claims::getSubject);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwt));
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private boolean isTokenExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }

    private Date extractExpiration(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }

    private <T> T extractClaim(String jwt, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(jwt);
        return resolver.apply(claims);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public long getRemainingValidity(String token) {
        return extractExpiration(token).getTime() - System.currentTimeMillis();
    }

}