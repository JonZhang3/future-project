package com.future.cache.support;

import java.time.Duration;
import java.util.function.Consumer;

import com.future.cache.properties.CacheProperties;
import com.github.benmanes.caffeine.cache.Caffeine;

final class Utils {

    private Utils() {
    }

    static Caffeine<Object, Object> createCaffeine(CacheProperties properties) {
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
