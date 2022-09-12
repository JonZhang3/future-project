package com.future.framework.common.utils;

import com.google.common.collect.Multimap;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class MapUtils {
    
    private MapUtils() {
    }

    /**
     * 从哈希表表中，获得 keys 对应的所有 value 数组
     *
     * @param multimap 哈希表
     * @param keys keys
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
    
}
