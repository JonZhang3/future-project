package com.future.cache.service;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisService {

    /**
     * 保存属性
     */
    void set(String key, Object value, Duration time);

    /**
     * 保存属性
     */
    void set(String key, Object value);

    /**
     * 获取属性
     */
    Object get(String key);

    /**
     * 删除属性
     */
    Boolean delete(String key);

    /**
     * 批量删除属性
     */
    Long delete(Collection<String> keys);

    /**
     * 设置过期时间
     */
    Boolean expire(String key, Duration timeout);

    /**
     * 获取过期时间
     */
    Duration getExpire(String key);

    /**
     * 判断是否有该属性
     */
    Boolean hasKey(String key);

    /**
     * 按delta递增
     */
    Long incr(String key, long delta);

    /**
     * 按delta递减
     */
    Long decr(String key, long delta);

    /**
     * 获取Hash结构中的属性
     */
    Object hget(String key, String hashKey);

    /**
     * 向Hash结构中放入一个属性
     */
    void hset(String key, String hashKey, Object value);

    /**
     * 直接获取整个Hash结构
     */
    Map<Object, Object> hgetall(String key);

    /**
     * 直接设置整个Hash结构
     */
    void hsetall(String key, Map<String, Object> map);

    /**
     * 删除Hash结构中的属性
     */
    void hdelete(String key, Object... hashKey);

    /**
     * 判断Hash结构中是否有该属性
     */
    Boolean hexists(String key, String hashKey);

    /**
     * Hash结构中属性递增
     */
    Long hincrby(String key, String hashKey, Long delta);

    /**
     * Hash结构中属性递减
     */
    Long hdecrby(String key, String hashKey, Long delta);

    /**
     * 获取Set结构
     */
    Set<Object> smembers(String key);

    /**
     * 向Set结构中添加属性
     */
    Long sadd(String key, Object... values);

    /**
     * 是否为Set中的属性
     */
    Boolean sismember(String key, Object member);

    /**
     * 获取Set结构的长度
     */
    Long ssize(String key);

    /**
     * 删除Set结构中的属性
     */
    Long srem(String key, Object... members);

    /**
     * 获取List结构中的属性
     */
    List<Object> lrange(String key, long start, long end);

    /**
     * 获取List结构的长度
     */
    Long llen(String key);

    /**
     * 根据索引获取List中的属性
     */
    Object lindex(String key, long index);

    /**
     * 向List结构中批量添加属性
     */
    Long lpush(String key, Object... values);

    /**
     * 从List结构中移除属性
     */
    Long lrem(String key, long count, Object value);
    
}
