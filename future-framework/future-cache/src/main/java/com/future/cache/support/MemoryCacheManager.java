package com.future.cache.support;

import org.jetbrains.annotations.NotNull;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import com.future.cache.properties.CacheProperties;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * 内存缓存管理器
 *
 * @author JonZhang
 */
public class MemoryCacheManager extends CaffeineCacheManager {

    private final Caffeine<Object, Object> cacheBuilder;

    private final boolean dynamic;

    public MemoryCacheManager(CacheProperties properties) {
        this.cacheBuilder = Utils.createCaffeine(properties);
        this.dynamic = properties.isDynamic();
        setAllowNullValues(properties.isAllowNullValues());
        if (properties.getCacheNames() != null && properties.getCacheNames().size() > 0) {
            setCacheNames(properties.getCacheNames());
        }
    }

    @Override
    public org.springframework.cache.Cache getCache(@NotNull String name) {
        Cache cache = super.getCache(name);
        if (cache == null && dynamic) {
            registerCustomCache(name, createNativeCaffeineCache(name));
            cache = super.getCache(name);
        }
        return cache;
    }

    @NotNull
    @Override
    protected com.github.benmanes.caffeine.cache.Cache<Object, Object> createNativeCaffeineCache(@NotNull String name) {
        return this.cacheBuilder.build();
    }

}
