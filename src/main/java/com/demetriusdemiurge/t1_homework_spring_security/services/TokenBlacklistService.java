package com.demetriusdemiurge.t1_homework_spring_security.services;

public interface TokenBlacklistService {
    void addToBlacklist(String token, long expirationMillis);
    boolean isBlacklisted(String token);
}
