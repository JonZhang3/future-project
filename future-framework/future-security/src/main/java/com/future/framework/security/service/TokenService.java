package com.future.framework.security.service;

import java.util.List;

import com.future.framework.security.domain.AccessTokenCheckResult;
import com.future.framework.security.domain.TokenResult;

public interface TokenService {

    /**
     * 生成Token
     * 
     * @param userId   用户 ID
     * @param userType 用户类型
     * @param scopes   数据权限范围
     */
    TokenResult createAccessToken(Long userId, Integer userType, List<String> scopes);

    /**
     * 检查 Token
     * 
     * @param accessToken
     * @return
     */
    AccessTokenCheckResult checkAccessToken(String accessToken);

    /**
     * 使用刷新Token 刷新访问 Token
     * @param refreshToken
     */
    TokenResult refreshAccessToken(String refreshToken);

    /**
     * 移除访问令牌
     *
     * @param accessToken 访问令牌
     * @return 访问令牌的信息
     */
    void removeAccessToken(String accessToken);

}
