package com.future.module.system.service.impl;

import com.future.framework.common.domain.PageResult;
import com.future.module.system.domain.entity.OAuth2AccessToken;
import com.future.module.system.domain.query.auth.OAuth2AccessTokenPageQuery;
import com.future.module.system.service.OAuth2TokenService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OAuth2TokenServiceImpl implements OAuth2TokenService {
    @Override
    public OAuth2AccessToken createAccessToken(Long userId, Integer userType, String clientId, List<String> scopes) {
        return null;
    }

    @Override
    public OAuth2AccessToken refreshAccessToken(String refreshToken, String clientId) {
        return null;
    }

    @Override
    public OAuth2AccessToken getAccessToken(String accessToken) {
        return null;
    }

    @Override
    public OAuth2AccessToken checkAccessToken(String accessToken) {
        return null;
    }

    @Override
    public OAuth2AccessToken removeAccessToken(String accessToken) {
        return null;
    }

    @Override
    public PageResult<OAuth2AccessToken> getAccessTokenPage(OAuth2AccessTokenPageQuery query) {
        return null;
    }
}
