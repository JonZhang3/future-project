package com.future.common.utils;

import com.future.common.core.domain.model.LoginUser;
import com.future.common.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 用户认证相关工具
 * 
 * @author JonZhang
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static Long getCurrentUserId() {
        return getCurrentUser().getUserId();
    }
    
    public static String getCurrentUsername() {
        return getCurrentUser().getUsername();
    }
    
    public static LoginUser getCurrentUser() {
        try {
            return (LoginUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new BusinessException("用户授权异常，请重新登录", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 对原始密码进行加密
     * @param originalPassword 原始密码
     * @return 加密后的字符串
     */
    public static String encodePassword(String originalPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(originalPassword);
    }

    /**
     * 判断密码是否匹配
     * @param rawPassword 真实密码
     * @param encodedPassword 加密后的密码
     * @return true 匹配
     */
    public static boolean passwordMatches(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    
}
