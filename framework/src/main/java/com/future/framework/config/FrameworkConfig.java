package com.future.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 全局配置类
 * 
 * @author JonZhang
 */
@Data
@Component
@ConfigurationProperties(prefix = "framework")
public class FrameworkConfig {

    /**
     * 是否启用跨域配置
     */
    private boolean cors = false;

    /**
     * JWT 配置
     */
    private Jwt jwt = new Jwt();

    @Data
    public static class Jwt {
        private String secret = "future";
    }

}
