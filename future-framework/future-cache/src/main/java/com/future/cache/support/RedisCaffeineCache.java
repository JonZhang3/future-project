package com.future.cache.support;

import java.util.concurrent.Callable;

import org.springframework.cache.support.AbstractValueAdaptingCache;

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
        // TODO Auto-generated method stub
        
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected Object lookup(Object key) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
