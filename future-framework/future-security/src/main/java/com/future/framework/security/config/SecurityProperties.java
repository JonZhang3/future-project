package com.future.framework.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Data
@Validated
@ConfigurationProperties(prefix = "future.security")
public class SecurityProperties {

    /**
     * Token 的存储位置，默认会将token存储到数据库中，可以查看在线用户，如果不需要此功能，。。。。。。
     * redis、mysql、none
     * 如果使用 redis 存储，需要指定 spring data redis
     */
    private String tokenStorage = "none";

    /**
     * 访问Token的持续时间，默认 2 小时
     */
    private Duration accessTokenDuration = Duration.ofHours(2);

    /**
     * 刷新 Token 的持续时间，默认 30 天
     */
    private Duration refreshTokenDuration = Duration.ofDays(30);

    /**
     * HTTP 请求时，访问令牌的请求 Header
     */
    @NotEmpty(message = "Token Header 不能为空")
    private String tokenHeader = "Authorization";

    /**
     * mock 模式的开关
     */
    @NotNull(message = "mock 模式的开关不能为空")
    private Boolean mockEnable = false;

    /**
     * mock 模式的密钥
     * 一定要配置密钥，保证安全性
     */
    @NotEmpty(message = "mock 模式的密钥不能为空") // 这里设置了一个默认值，因为实际上只有 mockEnable 为 true 时才需要配置。
    private String mockSecret = "test";

    /**
     * 免登录的 URL 列表
     */
    private List<String> permitUrls = Collections.emptyList();

}
