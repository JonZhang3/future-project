package com.future.module.system.security;

import com.future.framework.common.exception.CommonErrorCode;
import com.future.framework.common.exception.ServiceException;
import com.future.framework.common.utils.DateUtils;
import com.future.framework.security.domain.AccessTokenCheckResult;
import com.future.framework.security.service.TokenService;
import com.future.module.system.dao.AccessTokenMapper;
import com.future.module.system.dao.RefreshTokenMapper;
import com.future.module.system.domain.convert.AccessTokenConvert;
import com.future.module.system.domain.entity.AccessToken;
import com.future.module.system.domain.entity.RefreshToken;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    private AccessTokenMapper accessTokenMapper;
    @Resource
    private RefreshTokenMapper refreshTokenMapper;

    public AccessToken createAccessToken(Long userId, Integer userType, String clientId, List<String> scopes) {
        // 创建刷新令牌
        // 创建访问令牌
        return null;
    }
    
    @Override
    public AccessTokenCheckResult checkAccessToken(String token) {
        AccessToken accessToken = null;
        if (accessToken == null) {
            throw new ServiceException("访问令牌不存在", CommonErrorCode.UNAUTHORIZED.getCode());
        }
        if (DateUtils.isExpired(accessToken.getExpiresTime())) {
            throw new ServiceException("访问令牌已过期", CommonErrorCode.UNAUTHORIZED.getCode());
        }
        return AccessTokenConvert.INSTANCE.convert(accessToken);
    }

    public AccessToken getAccessToken(String accessToken) {
        
    }
    
    @Override
    public void removeAccessToken(String accessToken) {

    }
}
