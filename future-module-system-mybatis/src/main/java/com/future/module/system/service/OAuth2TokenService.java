package com.future.module.system.service;

import com.future.framework.common.domain.PageResult;
import com.future.module.system.domain.entity.OAuth2AccessToken;
import com.future.module.system.domain.query.auth.OAuth2AccessTokenPageQuery;

import java.util.List;

/**
 * OAuth2.0 Token Service 接口
 * <p>
 * 从功能上，和 Spring Security OAuth 的 DefaultTokenServices + JdbcTokenStore 的功能，提供访问令牌、刷新令牌的操作
 *
 * @author JonZhang
 */
public interface OAuth2TokenService {

    /**
     * 创建访问令牌
     * 注意：该流程中，会包含创建刷新令牌的创建
     * <p>
     * 参考 DefaultTokenServices 的 createAccessToken 方法
     *
     * @param userId   用户编号
     * @param userType 用户类型
     * @param clientId 客户端编号
     * @param scopes   授权范围
     * @return 访问令牌的信息
     */
    OAuth2AccessToken createAccessToken(Long userId, Integer userType, String clientId, List<String> scopes);

    /**
     * 刷新访问令牌
     * <p>
     * 参考 DefaultTokenServices 的 refreshAccessToken 方法
     *
     * @param refreshToken 刷新令牌
     * @param clientId     客户端编号
     * @return 访问令牌的信息
     */
    OAuth2AccessToken refreshAccessToken(String refreshToken, String clientId);

    /**
     * 获得访问令牌
     * <p>
     * 参考 DefaultTokenServices 的 getAccessToken 方法
     *
     * @param accessToken 访问令牌
     * @return 访问令牌的信息
     */
    OAuth2AccessToken getAccessToken(String accessToken);

    /**
     * 校验访问令牌
     *
     * @param accessToken 访问令牌
     * @return 访问令牌的信息
     */
    OAuth2AccessToken checkAccessToken(String accessToken);

    /**
     * 移除访问令牌
     * 注意：该流程中，会移除相关的刷新令牌
     * <p>
     * 参考 DefaultTokenServices 的 revokeToken 方法
     *
     * @param accessToken 刷新令牌
     * @return 访问令牌的信息
     */
    OAuth2AccessToken removeAccessToken(String accessToken);

    /**
     * 获得访问令牌分页
     *
     * @param query 请求
     * @return 访问令牌分页
     */
    PageResult<OAuth2AccessToken> getAccessTokenPage(OAuth2AccessTokenPageQuery query);

}
