package com.future.cache.support.cache;

import java.util.concurrent.Callable;

import org.springframework.cache.support.AbstractValueAdaptingCache;

public class MemoryAndRedisCache extends AbstractValueAdaptingCache {

    protected MemoryAndRedisCache(boolean allowNullValues) {
        super(allowNullValues);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Object getNativeCache() {
        return null;
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
