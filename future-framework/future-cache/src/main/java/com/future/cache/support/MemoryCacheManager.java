package com.future.cache.support;

import java.time.Duration;
import java.util.function.Consumer;

import org.springframework.cache.caffeine.CaffeineCacheManager;

import com.future.cache.properties.CacheProperties;
import org.springframework.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class MemoryCacheManager extends CaffeineCacheManager {

    private final Caffeine<Object, Object> cacheBuilder;

    private final boolean dynamic;

    public MemoryCacheManager(CacheProperties properties) {
        this.cacheBuilder = createCaffeine(properties);
        this.dynamic = properties.isDynamic();
        setAllowNullValues(properties.isAllowNullValues());
        setCacheNames(properties.getCacheNames());
    }

    @Override
    public org.springframework.cache.Cache getCache(String name) {
        Cache cache = super.getCache(name);
        if(cache == null && dynamic) {
            cache = createCaffeineCache(name);
        }
        return cache;
    }

    @Override
    protected com.github.benmanes.caffeine.cache.Cache<Object, Object> createNativeCaffeineCache(String name) {
        return this.cacheBuilder.build();
    }

    private static Caffeine<Object, Object> createCaffeine(CacheProperties properties) {
        Caffeine<Object, Object> cacheBuilder = Caffeine.newBuilder();
        doIfPresent(properties.getMemory().getExpireAfterAccess(), cacheBuilder::expireAfterAccess);
        doIfPresent(properties.getMemory().getExpireAfterWrite(), cacheBuilder::expireAfterWrite);
        doIfPresent(properties.getMemory().getRefreshAfterWrite(), cacheBuilder::refreshAfterWrite);
        if (properties.getMemory().getInitialCapacity() > 0) {
            cacheBuilder.initialCapacity(properties.getMemory().getInitialCapacity());
        }
        if (properties.getMemory().getMaximumSize() > 0) {
            cacheBuilder.maximumSize(properties.getMemory().getMaximumSize());
        }
        if (properties.getMemory().getKeyStrength() != null) {
            switch (properties.getMemory().getKeyStrength()) {
                case WEAK:
                    cacheBuilder.weakKeys();
                    break;
                case SOFT:
                    throw new UnsupportedOperationException("caffeine unsupport soft key reference");
                default:
            }
        }
        if (properties.getMemory().getValueStrength() != null) {
            switch (properties.getMemory().getValueStrength()) {
                case WEAK:
                    cacheBuilder.weakValues();
                    break;
                case SOFT:
                    cacheBuilder.softValues();
                default:
            }
        }
        return cacheBuilder;
    }

    private static void doIfPresent(Duration duration, Consumer<Duration> consumer) {
        if (duration != null && !duration.isNegative()) {
            consumer.accept(duration);
        }
    }

}
