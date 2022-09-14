package com.future.framework.security.token;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

@AllArgsConstructor
public class RedisTokenStorage implements TokenStorage {
    
    private final RedisTemplate<String, String> redisTemplate;
    
    @Override
    public void saveToken(TokenType type, String token) {
        
    }

    @Override
    public String getToken(TokenType type) {
        return null;
    }
}
