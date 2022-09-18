package com.future.cache;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.future.cache.properties.CacheProperties;
import com.future.cache.support.MemoryCacheManager;

/**
 * 多级缓存
 */
@EnableCaching
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties(CacheProperties.class)
public class MultilevelCacheAutoConfiguration {
    
    public CacheManager cacheManager(CacheProperties cacheProperties,
                                    RedisTemplate<String, Object> redisTemplate) {
        switch(cacheProperties.getType()) {
            case NONE:
                return new NoOpCacheManager();
            case MEMORY:
                return new MemoryCacheManager(cacheProperties);
            case REDIS:
                break;
            case ALL:
                break;
            default:
                return null;
        }
    }

    private CacheManager createRedisCacheManager(CacheProperties properties, RedisSerializer<Object> redisSerializer) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(properties.getRedis().getDefaultExpiration())
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer));
        if(!properties.isAllowNullValues()) {
            config.disableCachingNullValues();
        }
        return RedisCacheManager.builder().cacheDefaults(config).build();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory,
                                                       RedisSerializer<Object> redisSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
    
    @Bean
    public RedisSerializer<Object> redisSerializer() {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);
        return serializer;
    }

}
