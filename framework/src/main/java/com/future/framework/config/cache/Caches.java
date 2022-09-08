package com.future.framework.config.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Optional;

public final class Caches {

    private Caches() {
    }

    public static Cache getCache(String name) {
        return null;
    }

    /**
     * 获取 CacheManager
     */
    public static CacheManager getCacheManager() {
        return null;
    }

    public static void removeCache(final String name, final Object key) {
        CacheManager cacheManager = getCacheManager();
        if (cacheManager != null) {
            Optional.ofNullable(cacheManager.getCache(name))
                .ifPresent(cache -> cache.evict(key));
        }
    }

}
