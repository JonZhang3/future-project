package com.future.cache.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import com.future.cache.enums.CacheType;

import java.util.HashSet;
import java.util.Set;

@Data
@ConfigurationProperties(prefix = "future.cache")
public class CacheProperties {

    private Set<String> cacheNames = new HashSet<>();

    private CacheType type;// 缓存类型；none-没有缓存;memory-内存缓存;redis-Redis缓存;all-memory+redis

    /**
     * 是否存储空值，默认true，防止缓存穿透
     */
    private boolean allowNullValues = true;

    /**
     * 是否动态根据cacheName创建Cache的实现，默认true
     */
    private boolean dynamic = true;

    /**
     * 缓存key的前缀
     */
    private String cachePrefix;

    @NestedConfigurationProperty
    private RedisProperties redis = new RedisProperties();

    @NestedConfigurationProperty
    private MemoryProperties memory = new MemoryProperties();

}
