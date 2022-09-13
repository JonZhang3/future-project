package com.future.framework.common.utils;

import java.util.Date;

/**
 * 时间工具类
 *
 * @author JonZhang
 */
public final class DateUtils {
    
    private DateUtils() {
    }

    /**
     * 时区 - 默认
     */
    public static final String TIME_ZONE_DEFAULT = "GMT+8";

    /**
     * 秒转换成毫秒
     */
    public static final long SECOND_MILLIS = 1000;

    public static final String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";

    public static boolean isExpired(Date time) {
        return System.currentTimeMillis() > time.getTime();
    }
    
}
