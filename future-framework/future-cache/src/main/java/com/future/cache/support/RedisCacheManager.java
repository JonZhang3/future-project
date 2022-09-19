package com.future.cache.support;

import java.time.Duration;
import java.util.Collection;
import java.util.Map;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.StringUtils;

import com.future.cache.properties.CacheProperties;

/**
 * Redis 缓存管理器
 * 
 * @author JonZhang
 */
public class RedisCacheManager extends org.springframework.data.redis.cache.RedisCacheManager {

    private final Map<String, Duration> expires;

    public RedisCacheManager(RedisConnectionFactory connectionFactory,
            CacheProperties cacheProperties, RedisSerializer<Object> redisSerializer) {
        super(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory),
                createCacheConfig(cacheProperties, redisSerializer),
                toStringArray(cacheProperties.getCacheNames()));
        this.expires = cacheProperties.getRedis().getExpires();
    }

    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        Duration duration = expires.get(name);
        if (duration != null) {
            cacheConfig = cacheConfig.entryTtl(duration);
        }
        return super.createRedisCache(name, cacheConfig);
    }

    private static RedisCacheConfiguration createCacheConfig(CacheProperties cacheProperties,
            RedisSerializer<Object> redisSerializer) {
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(cacheProperties.getRedis().getDefaultExpiration())
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer));
        if (!cacheProperties.isAllowNullValues()) {
            cacheConfig = cacheConfig.disableCachingNullValues();
        }
        if (StringUtils.hasLength(cacheProperties.getRedis().getCachePrefix())) {
            cacheConfig = cacheConfig.computePrefixWith((cacheName) -> cacheProperties.getRedis().getCachePrefix());
        }
        return cacheConfig;
    }

    private static String[] toStringArray(Collection<String> coll) {
        if (coll == null) {
            return new String[0];
        }
        return coll.toArray(new String[0]);
    }

}
