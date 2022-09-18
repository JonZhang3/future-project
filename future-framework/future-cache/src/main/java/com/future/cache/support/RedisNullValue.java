package com.future.cache.support;

import java.io.Serializable;

public class RedisNullValue implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public static RedisNullValue REDIS_NULL_VALUE = new RedisNullValue();

}
