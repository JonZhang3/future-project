package com.future.framework.config.cache;

import lombok.Data;
import org.springframework.boot.autoconfigure.cache.CacheType;

import java.util.Set;

@Data
public class CacheProperties {

    private CacheType type;// 缓存类型，目前支持SIMPLE/REDIS/CAFFEINE
    private boolean primary;// 是否是主缓存
    private boolean enable = true;
    private Set<String> names;
    private Integer initialCapacity = 200;
    private Integer maximumSize = 1000;

}
