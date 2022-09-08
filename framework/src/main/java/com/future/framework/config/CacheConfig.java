package com.future.framework.config;

import com.future.framework.config.cache.CacheProperties;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Configuration
@EnableCaching
public class CacheConfig {

    @Resource(type = FrameworkConfig.class)
    private FrameworkConfig frameworkConfig;

    @Bean
    @Primary
    @ConditionalOnProperty(prefix = "framework", name = "caches", matchIfMissing = true)
    public CacheManager masterCacheManager() {
        CacheProperties config = getConfig(frameworkConfig.getCaches(), true);
        if(config == null) {
            return null;
        }
        return createCacheManager(config);
    }

    @Bean
    @ConditionalOnProperty(prefix = "framework", name = "caches", matchIfMissing = true)
    public CacheManager slaveCacheManager() {
        CacheProperties config = getConfig(frameworkConfig.getCaches(), false);
        if (config == null) {
            return null;
        }
        return createCacheManager(config);
    }

    private CacheProperties getConfig(List<CacheProperties> properties, boolean primary) {
        if (CollectionUtils.isEmpty(properties)) {
            return null;
        }
        return properties.stream().filter(i -> i.isPrimary() == primary)
            .findFirst().orElse(null);
    }

    private CacheManager createCacheManager(CacheProperties config) {
        switch (config.getType()) {
            case REDIS:
                return createRedisCacheManager(config);
            case CAFFEINE:
            case SIMPLE:
            default:
                return createCaffeineCacheManager(config);
        }
    }

    private CacheManager createCaffeineCacheManager(CacheProperties config) {
        Set<String> names = config.getNames();
        String[] nameArray;
        if(names == null || names.size() == 0) {
            nameArray = new String[0];
        } else {
            nameArray = names.toArray(new String[0]);
        }
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(nameArray);
        cacheManager.setCaffeine(Caffeine.newBuilder()
            .initialCapacity(config.getInitialCapacity())
            .maximumSize(config.getMaximumSize())
            .weakKeys()
            .recordStats());
        return cacheManager;
    }

    private CacheManager createRedisCacheManager(CacheProperties config) {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        
        return null;
    }

}
