package com.demetriusdemiurge.t1_homework_token.services;

public interface TokenBlacklistService {
    void addToBlacklist(String token, long expirationMillis);
    boolean isBlacklisted(String token);
}
