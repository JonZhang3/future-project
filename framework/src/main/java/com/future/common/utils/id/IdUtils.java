package com.future.common.utils.id;

/**
 * ID 生成帮助类
 * 
 * @author JonZhang
 */
public final class IdUtils {

    private static final ShortSnowflakeId SNOWFLAKE_ID = new ShortSnowflakeId(1);

    /**
     * 生成短雪花ID，对JS友好
     */
    public static Long shortSnowflakeId() {
        return SNOWFLAKE_ID.next();
    }

    /**
     * 获取随机UUID
     *
     * @return 随机UUID
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUUID() {
        return UUID.randomUUID().toString(true);
    }

    /**
     * 获取随机UUID，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 随机UUID
     */
    public static String fastUUID() {
        return UUID.fastUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String fastSimpleUUID() {
        return UUID.fastUUID().toString(true);
    }

}
