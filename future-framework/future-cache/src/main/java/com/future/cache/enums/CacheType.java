package com.future.cache.enums;

/**
 * 缓存类型
 * 
 * @author JonZhang
 */
public enum CacheType {

    NONE, // 没有缓存
    MEMORY, // 使用内存缓存
    REDIS, // 使用 Redis 缓存
    ALL;// 使用内存缓存 + Redis 两级缓存

}
