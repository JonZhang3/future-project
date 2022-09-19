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
import com.future.framework.security.token.TokenType;
import com.future.framework.security.util.TokenUtils;
import com.future.module.system.dao.AccessTokenMapper;
import com.future.module.system.dao.RefreshTokenMapper;
import com.future.module.system.domain.convert.AccessTokenConvert;
import com.future.module.system.domain.entity.AccessToken;
import com.future.module.system.domain.entity.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Calendar;
import java.util.List;

@Service
public class TokenServiceImpl implements TokenService {

    private static final String CACHE_NAME = "$Token$";
    
    @Resource
    private SecurityProperties securityProperties;
    
    @Resource
    private AccessTokenMapper accessTokenMapper;
    
    @Resource
    private RefreshTokenMapper refreshTokenMapper;

    @Autowired(required = false)
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public TokenResult createAccessToken(Long userId, Integer userType, List<String> scopes) {
        LoginUser loginUser = new LoginUser().setId(userId).setUserType(userType).setScopes(scopes);
        RefreshToken refreshToken = createRefreshToken(loginUser, scopes);
        AccessToken accessToken = createAccessToken(loginUser, refreshToken.getRefreshToken(), scopes);
        return new TokenResult(accessToken.getAccessToken(), accessToken.getRefreshToken());
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
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }
        // 1.首先验证 jwt 是否有效
        LoginUser loginUser = TokenUtils.validateAndGetPayload(accessToken);
        if (loginUser == null) {// token 无效
            return null;
        }
        // 2.从 redis 中获取
        if(redisTemplate != null) {
            Object o = redisTemplate.opsForValue().get(getKeyPrefix(TokenType.ACCESS_TOKEN) + loginUser.getId().toString());
            
        }
        return null;
    }

    @Override
    public void removeAccessToken(String accessToken) {
        LoginUser user = TokenUtils.parse(accessToken);
        
    }

    @Override
    public TokenResult refreshAccessToken(String refreshToken) {
        LoginUser loginUser = TokenUtils.validateAndGetPayload(refreshToken);
        if (loginUser == null) {
            return null;
        }
        String accessToken = TokenUtils.generate(loginUser, securityProperties.getAccessTokenDuration());
        return new TokenResult(accessToken, refreshToken, loginUser.getId());
    }

    private AccessToken createAccessToken(LoginUser loginUser, String refreshToken, List<String> scopes) {
        String accessToken = TokenUtils.generate(loginUser, securityProperties.getAccessTokenDuration());
        AccessToken result = new AccessToken().setAccessToken(accessToken).setRefreshToken(refreshToken)
            .setUserId(loginUser.getId()).setUserType(loginUser.getUserType())
            .setScopes(scopes)
            .setExpiresTime(DateUtils.addDate(Calendar.SECOND, (int) securityProperties.getAccessTokenDuration().getSeconds()));
        accessTokenMapper.insert(result);
        saveTokenToCache(TokenType.ACCESS_TOKEN, loginUser.getId().toString(), accessToken, securityProperties.getAccessTokenDuration());
        return result;
    }
    
    private RefreshToken createRefreshToken(LoginUser loginUser, List<String> scopes) {
        String refreshToken = TokenUtils.generate(loginUser, securityProperties.getRefreshTokenDuration());
        RefreshToken result = new RefreshToken().setRefreshToken(refreshToken)
            .setUserId(loginUser.getId()).setUserType(loginUser.getUserType())
            .setScopes(scopes)
            .setExpiresTime(DateUtils.addDate(Calendar.SECOND, (int) securityProperties.getRefreshTokenDuration().getSeconds()));
        refreshTokenMapper.insert(result);
        saveTokenToCache(TokenType.REFRESH_TOKEN, loginUser.getId().toString(), refreshToken, securityProperties.getRefreshTokenDuration());
        return result;
    }

    private void saveTokenToCache(TokenType type, String key, Object value, Duration duration) {
        if(redisTemplate != null) {
            redisTemplate.opsForValue().set(getKeyPrefix(type) + key, value, duration);
        }
    }
    
    private static String getKeyPrefix(TokenType type) {
        if(TokenType.ACCESS_TOKEN.equals(type)) {
            return "token:access:";
        }
        return "token:refresh:";
    }
    
}
