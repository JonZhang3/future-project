package com.future.security.sa.util;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class SecurityUtils {
    
    private SecurityUtils() {
    }

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    
    public static void login(Long userId) {
        StpUtil.login(userId);
    }
    
    public static void logout() {
        StpUtil.logout();
    }
    
    public static String encodePassword(String originalPassword) {
        return PASSWORD_ENCODER.encode(originalPassword);
    }
    
    public static boolean matches(String rawPassword, String encodedPassword) {
        return PASSWORD_ENCODER.matches(rawPassword, encodedPassword);
    }
    
    /**
     * 获取当前会话用户 ID
     */
    public static Long getUserId() {
        return StpUtil.getLoginIdAsLong();
    }

    /**
     * 获取当前会话的 Token
     */
    public static String getToken() {
        return StpUtil.getTokenValue();
    }

    /**
     * 获取 Token 名称
     */
    public static String getTokenName() {
        return StpUtil.getTokenName();
    }
    
}
