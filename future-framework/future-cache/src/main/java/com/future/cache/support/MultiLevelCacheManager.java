package com.future.cache.support;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.future.cache.properties.CacheProperties;

/**
 * 多级缓存管理器。先使用内存缓存，再使用 Redis 缓存
 * 
 * @author JonZhang
 */
public class MultiLevelCacheManager implements CacheManager {

    private Map<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>();

    private final MemoryCacheManager memoryCacheManager;
    private final RedisCacheManager redisCacheManager;

    private CacheProperties cacheProperties;
    private final boolean dynamic;

    public MultiLevelCacheManager(CacheProperties cacheProperties,
            RedisConnectionFactory connectionFactory, RedisSerializer<Object> redisSerializer) {
        this.cacheProperties = cacheProperties;
        this.dynamic = cacheProperties.isDynamic();
        this.memoryCacheManager = new MemoryCacheManager(cacheProperties);
        this.redisCacheManager = new RedisCacheManager(connectionFactory, cacheProperties, redisSerializer);
    }

    @Override
    public Cache getCache(String name) {
        Cache cache = cacheMap.get(name);
        if (cache != null) {
            return cache;
        }
        if (!dynamic) {
            return null;
        }
        cache = new MultiLevelCache(cacheProperties.isAllowNullValues(), name, memoryCacheManager.getCache(name),
                redisCacheManager.getCache(name));
        cacheMap.putIfAbsent(name, cache);
        return cache;
    }

    @Override
    public Collection<String> getCacheNames() {
        return null;
    }

}
