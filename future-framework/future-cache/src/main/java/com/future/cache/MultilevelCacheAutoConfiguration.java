package com.future.cache;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.future.cache.properties.CacheProperties;
import com.future.cache.support.MemoryCacheManager;
import com.future.cache.support.MultiLevelCacheManager;
import com.future.cache.support.RedisCacheManager;

/**
 * 多级缓存
 */
@EnableCaching
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties(CacheProperties.class)
public class MultilevelCacheAutoConfiguration {

    @Bean
    public CacheManager cacheManager(CacheProperties cacheProperties,
            @Autowired(required = false) RedisTemplate<String, Object> redisTemplate,
            @Autowired(required = false) RedisSerializer<Object> redisSerializer) {
        switch (cacheProperties.getType()) {
            case NONE:
                return new NoOpCacheManager();
            case MEMORY:
                return new MemoryCacheManager(cacheProperties);
            case REDIS:
                if (redisTemplate == null) {
                    throw new BeanCreationException("create CacheManager error, reason: RedisTemplate is null");
                }
                return new RedisCacheManager(redisTemplate.getConnectionFactory(), cacheProperties, redisSerializer);
            case ALL:
                if (redisTemplate == null) {
                    throw new BeanCreationException("create CacheManager error, reason: RedisTemplate is null");
                }
                return new MultiLevelCacheManager(cacheProperties, redisTemplate.getConnectionFactory(),
                        redisSerializer);
            default:
                throw new BeanCreationException("create CacheManager bean error, reason: no specified type");
        }
    }

    @Bean
    @DependsOn("redisSerializer")
    @ConditionalOnBean(RedisConnectionFactory.class)
    @ConditionalOnProperty(prefix = "spring", name = "redis")
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
    @ConditionalOnBean(RedisConnectionFactory.class)
    public RedisSerializer<Object> redisSerializer() {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);
        return serializer;
    }

}
