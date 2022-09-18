package com.future.cache.support;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import com.future.cache.properties.CacheProperties;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MultiLevelCacheManager implements CacheManager {

    private Map<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>();

    private CacheProperties cacheProperties;

    @Override
    public Cache getCache(String name) {
        Cache cache = cacheMap.get(name);
        if(cache != null) {
            return cache;
        }
        if(!cacheProperties.isDynamic()) {
            return null;
        }
        
        return null;
    }

    @Override
    public Collection<String> getCacheNames() {
        return null;
    }
    
}
