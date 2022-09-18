package com.future.framework.common.utils;

import cn.hutool.core.util.StrUtil;

/**
 * 字符串工具类
 * 
 * @author JonZhang
 */
public final class StringUtils extends StrUtil {

    private StringUtils() {
    }

    public static final String COMMA = ",";

    public static String maxLength(CharSequence string, int length) {
        if (null == string) {
            return null;
        }
        if (string.length() <= length) {
            return string.toString();
        }
        return string.subSequence(0, length) + "...";
    }
    
}
