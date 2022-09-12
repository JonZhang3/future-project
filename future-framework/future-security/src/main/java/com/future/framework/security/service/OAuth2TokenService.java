package com.future.framework.security.service;

import com.future.framework.security.domain.OAuth2AccessTokenCheckRespDTO;

public interface OAuth2TokenService {

    OAuth2AccessTokenCheckRespDTO checkAccessToken(String accessToken);
    
}
