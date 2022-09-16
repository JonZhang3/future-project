package com.future.cache.support;

import org.springframework.cache.support.AbstractValueAdaptingCache;

import java.util.concurrent.Callable;

public class RedisCaffeineCache extends AbstractValueAdaptingCache {

    protected RedisCaffeineCache(boolean allowNullValues) {
        super(allowNullValues);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Object getNativeCache() {
        return this;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }

    @Override
    public void put(Object key, Object value) {
        
    }

    @Override
    public void evict(Object key) {
        
    }

    @Override
    public void clear() {
        
    }

    @Override
    protected Object lookup(Object key) {
        return null;
    }
    
}
