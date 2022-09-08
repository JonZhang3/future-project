package com.future.framework.config;

import com.future.framework.config.cache.CacheProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

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

    // 文件上传路径
    private static String profile;

    /**
     * JWT 配置
     */
    private Jwt jwt = new Jwt();
    
    private List<CacheProperties> caches;

    @Data
    public static class Jwt {
        private String issuer = "future";
        // 请求头名称
        private String headerName = "X-Access-Token";
        // 持续时间
        private Duration duration;
    }

    public static String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        FrameworkConfig.profile = profile;
    }

    /**
     * 获取导入上传路径
     */
    public static String getImportPath() {
        return getProfile() + "/import";
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath() {
        return getProfile() + "/avatar";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath() {
        return getProfile() + "/upload";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath() {
        return getProfile() + "/download/";
    }

}
