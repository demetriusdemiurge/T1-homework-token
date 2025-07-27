package com.demetriusdemiurge.t1_homework_token.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisTokenBlacklistService implements TokenBlacklistService {

    private final StringRedisTemplate redisTemplate;

    @Override
    public void addToBlacklist(String token, long expirationMillis) {
        redisTemplate.opsForValue().set(token, "blacklisted", expirationMillis, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean isBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }
}
