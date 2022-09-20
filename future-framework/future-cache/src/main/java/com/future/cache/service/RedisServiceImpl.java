package com.future.cache.service;

import com.future.cache.properties.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@ConditionalOnClass(name = "redisTemplate")
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private CacheProperties cacheProperties;

    @Override
    public void set(String key, Object value, Duration timeout) {
        redisTemplate.opsForValue().set(computeKey(key), value, timeout);
    }

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(computeKey(key), value);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(computeKey(key));
    }

    @Override
    public Boolean delete(String key) {
        return redisTemplate.delete(computeKey(key));
    }

    @Override
    public Long delete(Collection<String> keys) {
        keys = keys.stream().map(this::computeKey).collect(Collectors.toList());
        return redisTemplate.delete(keys);
    }

    @Override
    public Boolean expire(String key, Duration timeout) {
        return redisTemplate.expire(computeKey(key), timeout);
    }

    @Override
    public Duration getExpire(String key) {
        Long expire = redisTemplate.getExpire(computeKey(key), TimeUnit.SECONDS);
        if (expire == null) {
            expire = 0L;
        }
        return Duration.ofSeconds(expire);
    }

    @Override
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(computeKey(key));
    }

    @Override
    public Long incr(String key, long delta) {
        return redisTemplate.opsForValue().increment(computeKey(key), delta);
    }

    @Override
    public Long decr(String key, long delta) {
        return redisTemplate.opsForValue().decrement(computeKey(key), delta);
    }

    @Override
    public Object hget(String key, String hashKey) {
        return redisTemplate.opsForHash().get(computeKey(key), hashKey);
    }

    @Override
    public void hset(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(computeKey(key), hashKey, value);
    }

    @Override
    public Map<Object, Object> hgetall(String key) {
        return redisTemplate.opsForHash().entries(computeKey(key));
    }

    @Override
    public void hsetall(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(computeKey(key), map);
    }

    @Override
    public void hdelete(String key, Object... hashKey) {
        redisTemplate.opsForHash().delete(computeKey(key), hashKey);
    }

    @Override
    public Boolean hexists(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(computeKey(key), hashKey);
    }

    @Override
    public Long hincrby(String key, String hashKey, Long delta) {
        return redisTemplate.opsForHash().increment(computeKey(key), hashKey, delta);
    }

    @Override
    public Long hdecrby(String key, String hashKey, Long delta) {
        return redisTemplate.opsForHash().increment(computeKey(key), hashKey, -delta);
    }

    @Override
    public Set<Object> smembers(String key) {
        return redisTemplate.opsForSet().members(computeKey(key));
    }

    @Override
    public Long sadd(String key, Object... values) {
        return redisTemplate.opsForSet().add(computeKey(key), values);
    }

    @Override
    public Boolean sismember(String key, Object member) {
        return redisTemplate.opsForSet().isMember(computeKey(key), member);
    }

    @Override
    public Long ssize(String key) {
        return redisTemplate.opsForSet().size(computeKey(key));
    }

    @Override
    public Long srem(String key, Object... members) {
        return redisTemplate.opsForSet().remove(computeKey(key), members);
    }

    @Override
    public List<Object> lrange(String key, long start, long end) {
        return redisTemplate.opsForList().range(computeKey(key), start, end);
    }

    @Override
    public Long llen(String key) {
        return redisTemplate.opsForList().size(computeKey(key));
    }

    @Override
    public Object lindex(String key, long index) {
        return redisTemplate.opsForList().index(computeKey(key), index);
    }

    @Override
    public Long lpush(String key, Object... values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    @Override
    public Long lrem(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    private String computeKey(String key) {
        String keyPrefix = cacheProperties.getRedis().getKeyPrefix();
        if (keyPrefix != null && keyPrefix.length() > 0) {
            return keyPrefix + key;
        }
        return key;
    }

}
