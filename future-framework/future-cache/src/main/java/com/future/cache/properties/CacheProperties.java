package com.future.cache.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Data
@ConfigurationProperties(prefix = "future.cache")
public class CacheProperties {
    
    private Set<String> cacheNames = new HashSet<>();

    /**
     * 是否存储空值，默认true，防止缓存穿透
     */
    private boolean cacheNullValues = true;

    /**
     * 是否动态根据cacheName创建Cache的实现，默认true
     */
    private boolean dynamic = true;

    /**
     * 缓存key的前缀
     */
    @Resource
    private String cachePrefix;
    
    
    
}
