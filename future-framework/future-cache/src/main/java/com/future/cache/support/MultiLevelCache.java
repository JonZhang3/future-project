package com.future.cache.support;

import java.util.concurrent.Callable;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractValueAdaptingCache;

public class MultiLevelCache extends AbstractValueAdaptingCache {

    private final String name;

    private final Cache memoryCache;
    private final Cache redisCache;

    protected MultiLevelCache(boolean allowNullValues, String name,
            Cache memoryCache, Cache redisCache) {
        super(allowNullValues);
        this.name = name;
        this.memoryCache = memoryCache;
        this.redisCache = redisCache;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        T value = null;
        if (memoryCache != null) {
            value = memoryCache.get(key, valueLoader);
        }
        if (value == null) {
            value = redisCache.get(key, valueLoader);
            if (value != null) {
                memoryCache.putIfAbsent(key, value);
            }
        }
        return value;
    }

    @Override
    public void put(Object key, Object value) {
        if (memoryCache != null) {
            memoryCache.put(key, value);
        }
        redisCache.put(key, value);
    }

    @Override
    public void evict(Object key) {
        redisCache.evict(key);
        if (memoryCache != null) {
            memoryCache.evict(key);
        }
    }

    @Override
    public void clear() {
        redisCache.clear();
        if (memoryCache != null) {
            memoryCache.clear();
        }
    }

    @Override
    protected Object lookup(Object key) {
        return null;
    }

}
