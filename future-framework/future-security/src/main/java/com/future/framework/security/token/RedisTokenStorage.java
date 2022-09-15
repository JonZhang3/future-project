package com.future.framework.security.token;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

@AllArgsConstructor
public class RedisTokenStorage implements TokenStorage {
    
    private final RedisTemplate<String, Object> redisTemplate;
    
    @Override
    public void saveToken(TokenType type, String key, String token) {
        redisTemplate.opsForValue().set(getPrefix(type) + key, token);
    }

    @Override
    public String getToken(TokenType type, String key) {
        return redisTemplate.opsForValue().get(getPrefix(type) + key).toString();
    }

    @Override
    public void removeToken(TokenType type, String key) {
        redisTemplate.opsForValue().getAndDelete(getPrefix(type) + key);
    }
}
