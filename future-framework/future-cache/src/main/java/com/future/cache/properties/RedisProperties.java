package com.future.cache.properties;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class RedisProperties {
    
    /**
	 * 全局过期时间，默认不过期
	 */
	private Duration defaultExpiration = Duration.ZERO;

	/**
	 * 全局空值过期时间，默认和有值的过期时间一致，一般设置空值过期时间较短
	 */
	private Duration defaultNullValuesExpiration = null;

	/**
	 * 每个cacheName的过期时间，优先级比defaultExpiration高
	 */
	private Map<String, Duration> expires = new HashMap<>();
    
}
