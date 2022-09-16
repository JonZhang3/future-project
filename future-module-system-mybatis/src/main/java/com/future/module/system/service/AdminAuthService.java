package com.future.module.system.service;

import com.future.module.system.domain.entity.User;
import com.future.module.system.domain.query.auth.AuthLoginQuery;
import com.future.module.system.domain.vo.auth.AuthLoginVO;

import javax.validation.Valid;

/**
 * 管理后台的认证 Service 接口
 * <p>
 * 提供用户的登录、登出的能力
 *
 * @author JonZhang
 */
public interface AdminAuthService {

    /**
     * 验证账号 + 密码。如果通过，则返回用户
     *
     * @param username 账号
     * @param password 密码
     * @return 用户
     */
    User authenticate(String username, String password);

    /**
     * 账号登录
     *
     * @param query 登录信息
     * @return 登录结果
     */
    AuthLoginVO login(@Valid AuthLoginQuery query);

    /**
     * 基于 token 退出登录
     *
     * @param token   token
     * @param logType 登出类型
     */
    void logout(String token, Integer logType);

    /**
     * 刷新访问令牌
     *
     * @param refreshToken 刷新令牌
     * @return 登录结果
     */
    AuthLoginVO refreshToken(String refreshToken);

}
