package com.future.framework.security.service;

import com.future.framework.security.domain.AccessTokenCheckResult;

public interface TokenService {

    AccessTokenCheckResult checkAccessToken(String accessToken);

    /**
     * 移除访问令牌
     *
     * @param accessToken 访问令牌
     * @return 访问令牌的信息
     */
    void removeAccessToken(String accessToken);
    
}
