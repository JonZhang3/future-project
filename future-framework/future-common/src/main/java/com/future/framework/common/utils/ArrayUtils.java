package com.future.framework.common.utils;

import java.util.StringJoiner;

import cn.hutool.core.util.ArrayUtil;

/**
 * 数组工具类
 *
 * @author JonZhang
 */
public final class ArrayUtils extends ArrayUtil {

    private ArrayUtils() {
    }

    public static <T> String join(T[] array, CharSequence delimiter) {
        return join(array, delimiter, null, null);
    }

    public static <T> String join(T[] array, CharSequence delimiter, String prefix, String suffix) {
        if (array == null) {
            return "";
        }
        String innerPrefix = prefix == null ? "" : prefix;
        String innerSuffix = suffix == null ? "" : suffix;
        StringJoiner joiner = new StringJoiner(delimiter);
        for (T t : array) {
            joiner.add(innerPrefix + (t == null ? "" : t.toString()) + innerSuffix);
        }
        return joiner.toString();
    }

}
