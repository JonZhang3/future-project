package com.future.framework.common.utils;

import com.google.common.collect.Multimap;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class MapUtils {

    private MapUtils() {
    }

    /**
     * 从哈希表表中，获得 keys 对应的所有 value 数组
     *
     * @param multimap 哈希表
     * @param keys     keys
     * @return value 数组
     */
    public static <K, V> List<V> getList(Multimap<K, V> multimap, Collection<K> keys) {
        List<V> result = new ArrayList<>();
        keys.forEach(k -> {
            Collection<V> values = multimap.get(k);
            if (CollectionUtils.isEmpty(values)) {
                return;
            }
            result.addAll(values);
        });
        return result;
    }

    /**
     * 从哈希表查找到 key 对应的 value，然后进一步处理
     * 注意，如果查找到的 value 为 null 时，不进行处理
     *
     * @param map      哈希表
     * @param key      key
     * @param consumer 进一步处理的逻辑
     */
    public static <K, V> void findAndThen(Map<K, V> map, K key, Consumer<V> consumer) {
        if (org.apache.commons.collections4.MapUtils.isEmpty(map)) {
            return;
        }
        V value = map.get(key);
        if (value == null) {
            return;
        }
        consumer.accept(value);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return org.apache.commons.collections4.MapUtils.isEmpty(map);
    }
    
    public static boolean isNotEmpty(Map<?, ?> map) {
        return org.apache.commons.collections4.MapUtils.isNotEmpty(map);
    }

}