package com.future.module.system.security;

import com.future.framework.security.token.TokenStorage;
import com.future.framework.security.token.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class TokenStorageImpl implements TokenStorage {

    private static final String CACHE_NAME = "$Token$";

    @Autowired(required = false)
    private CacheManager cacheManager;

    @Override
    public void saveToken(TokenType type, String key, String token) {
        if (cacheManager != null) {
            Cache cache = cacheManager.getCache(CACHE_NAME);
            if (cache != null) {
                cache.put(getPrefix(type) + key, token);
            }
        }
    }

    @Override
    public String getToken(TokenType type, String key) {
        if (cacheManager != null) {
            Cache cache = cacheManager.getCache(CACHE_NAME);
            if (cache != null) {
                return cache.get(getPrefix(type) + key, String.class);
            }
        }
        return null;
    }

    @Override
    public void removeToken(TokenType type, String key) {
        if(cacheManager != null) {
            Cache cache = cacheManager.getCache(CACHE_NAME);
            if(cache != null) {
                cache.evict(getPrefix(type) + key);
            }
        }
    }
    
    
    
}
