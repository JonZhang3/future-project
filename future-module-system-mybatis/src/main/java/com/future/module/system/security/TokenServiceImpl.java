package com.future.module.system.security;

import com.future.framework.common.exception.CommonErrorCode;
import com.future.framework.common.exception.ServiceException;
import com.future.framework.common.utils.DateUtils;
import com.future.framework.common.utils.StringUtils;
import com.future.framework.security.config.SecurityProperties;
import com.future.framework.security.domain.AccessTokenCheckResult;
import com.future.framework.security.domain.LoginUser;
import com.future.framework.security.domain.TokenResult;
import com.future.framework.security.service.TokenService;
import com.future.framework.security.token.TokenStorage;
import com.future.framework.security.token.TokenType;
import com.future.framework.security.util.TokenUtils;
import com.future.module.system.domain.convert.AccessTokenConvert;
import com.future.module.system.domain.entity.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    private SecurityProperties securityProperties;

    @Autowired(required = false)
    private TokenStorage tokenStorage;

    @Override
    public TokenResult createAccessToken(Long userId, Integer userType, List<String> scopes) {
        LoginUser loginUser = new LoginUser().setId(userId).setUserType(userType).setScopes(scopes);
        String accessToken = TokenUtils.generate(loginUser, securityProperties.getAccessTokenDuration());
        String refreshToken = TokenUtils.generate(loginUser, securityProperties.getRefreshTokenDuration());
        // 存储 Token
        if (tokenStorage != null) {
            tokenStorage.saveToken(TokenType.ACCESS_TOKEN, userId + "", accessToken);
            tokenStorage.saveToken(TokenType.REFRESH_TOKEN, userId + "", refreshToken);
        }
        return new TokenResult(accessToken, refreshToken);
    }

    @Override
    public AccessTokenCheckResult checkAccessToken(String token) {
        AccessToken accessToken = getAccessToken(token);
        if (accessToken == null) {
            throw new ServiceException("访问令牌不存在", CommonErrorCode.UNAUTHORIZED.getCode());
        }
        if (DateUtils.isExpired(accessToken.getExpiresTime())) {
            throw new ServiceException("访问令牌已过期", CommonErrorCode.UNAUTHORIZED.getCode());
        }
        return AccessTokenConvert.INSTANCE.convert(accessToken);
    }

    public AccessToken getAccessToken(String accessToken) {
        if(StringUtils.isBlank(accessToken)) {
            return null;
        }
        LoginUser loginUser = TokenUtils.validateAndGetPayload(accessToken);
        if(loginUser == null) {// token 无效
            return null;
        }
        if(tokenStorage != null) {
            String storageToken = tokenStorage.getToken(TokenType.ACCESS_TOKEN, loginUser.getId().toString());
            if(StringUtils.isEmpty(storageToken)) {
                return null;
            }
            
        }
        return null;
    }

    @Override
    public void removeAccessToken(String accessToken) {
        LoginUser user = TokenUtils.parse(accessToken);
        if(user != null && tokenStorage != null) {
            tokenStorage.removeToken(TokenType.ACCESS_TOKEN, user.getId().toString());
            tokenStorage.removeToken(TokenType.REFRESH_TOKEN, user.getId().toString());
        }
    }

    @Override
    public TokenResult refreshAccessToken(String refreshToken) {
        LoginUser loginUser = TokenUtils.validateAndGetPayload(refreshToken);
        if(loginUser == null) {
            return null;
        }
        String accessToken = TokenUtils.generate(loginUser, securityProperties.getAccessTokenDuration());
        return new TokenResult(accessToken, refreshToken, loginUser.getId());
    }

}
