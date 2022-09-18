package com.future.module.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.future.framework.common.constant.enums.CommonStatus;
import com.future.framework.common.constant.enums.LoginLogType;
import com.future.framework.common.constant.enums.LoginResult;
import com.future.framework.common.exception.ServiceException;
import com.future.framework.common.utils.ServletUtils;
import com.future.framework.common.utils.ValidationUtils;
import com.future.framework.security.domain.TokenResult;
import com.future.framework.security.service.TokenService;
import com.future.framework.security.util.SecurityUtils;
import com.future.module.system.constants.enums.SystemErrorCode;
import com.future.module.system.domain.entity.LoginLog;
import com.future.module.system.domain.entity.User;
import com.future.module.system.domain.query.auth.AuthLoginQuery;
import com.future.module.system.domain.vo.auth.AuthLoginVO;
import com.future.module.system.service.AdminAuthService;
import com.future.module.system.service.CaptchaService;
import com.future.module.system.service.LoginLogService;
import com.future.module.system.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.Objects;

import static com.future.module.system.constants.enums.SystemErrorCode.*;

@Service
public class AdminAuthServiceImpl implements AdminAuthService {

    @Resource
    private UserService userService;
    @Resource
    private LoginLogService loginLogService;
    @Resource
    private CaptchaService captchaService;
    @Resource
    private TokenService tokenService;

    @Resource
    private Validator validator;

    @Override
    public User authenticate(String username, String password) {
        final LoginLogType logTypeEnum = LoginLogType.LOGIN_USERNAME;
        // 如果需要同时支持邮箱、手机号登录，可在此修改
        User user = userService.getUserByUsername(username);
        if (user == null) {
            createLoginLog(null, username, logTypeEnum, LoginResult.BAD_CREDENTIALS);
            throw new ServiceException(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        if (!userService.isPasswordMatch(password, user.getPassword())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResult.BAD_CREDENTIALS);
            throw new ServiceException(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // 校验是否禁用
        if (ObjectUtil.notEqual(user.getStatus(), CommonStatus.VALID.getValue())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResult.USER_DISABLED);
            throw new ServiceException(AUTH_LOGIN_USER_DISABLED);
        }
        return user;
    }

    @Override
    public AuthLoginVO login(AuthLoginQuery query) {
        // 判断验证码是否正确
        verifyCaptcha(query);

        // 使用账号密码，进行登录
        User user = authenticate(query.getUsername(), query.getPassword());
        // 创建 Token 令牌，记录登录日志
        TokenResult tokens = tokenService.createAccessToken(user.getId(), user.getUserType(), null);
        // 插入登陆日志
        createLoginLog(user.getId(), user.getUsername(), LoginLogType.LOGIN_USERNAME, LoginResult.SUCCESS);
        AuthLoginVO vo = new AuthLoginVO();
        vo.setUserId(user.getId());
        vo.setAccessToken(tokens.getAccessToken());
        vo.setRefreshToken(tokens.getRefreshToken());
        return vo;
    }

    @Override
    public void logout(String token, Integer logType) {
        // 删除访问令牌
        tokenService.removeAccessToken(token);
        // 删除成功，则记录登出日志
        Long userId = SecurityUtils.getLoginUserId();
        if (userId != null) {
            createLogoutLog(SecurityUtils.getLoginUserId(), logType);
        }
    }

    @Override
    public AuthLoginVO refreshToken(String refreshToken) {
        TokenResult tokens = tokenService.refreshAccessToken(refreshToken);
        if(tokens == null) {
            throw new ServiceException(SystemErrorCode.AUTH_TOKEN_EXPIRED);
        }
        AuthLoginVO vo = new AuthLoginVO();
        vo.setUserId(tokens.getUserId());
        vo.setAccessToken(tokens.getAccessToken());
        vo.setRefreshToken(tokens.getRefreshToken());
        return vo;
    }

    void verifyCaptcha(AuthLoginQuery query) {
        // 如果验证码关闭，则不进行校验
        if (!captchaService.isCaptchaEnable()) {
            return;
        }
        // 校验验证码
        ValidationUtils.validate(validator, query, AuthLoginQuery.CodeEnableGroup.class);
        // 验证码不存在
        final LoginLogType logType = LoginLogType.LOGIN_USERNAME;
        String code = captchaService.getCaptchaCode(query.getUuid());
        if (code == null) {
            // 创建登录失败日志（验证码不存在）
            createLoginLog(null, query.getUsername(), logType, LoginResult.CAPTCHA_NOT_FOUND);
            throw new ServiceException(AUTH_LOGIN_CAPTCHA_NOT_FOUND);
        }
        // 验证码不正确
        if (!code.equals(query.getCode())) {
            // 创建登录失败日志（验证码不正确)
            createLoginLog(null, query.getUsername(), logType, LoginResult.CAPTCHA_CODE_ERROR);
            throw new ServiceException(AUTH_LOGIN_CAPTCHA_CODE_ERROR);
        }
        // 正确，所以要删除下验证码
        captchaService.deleteCaptchaCode(query.getUuid());
    }

    private void createLoginLog(Long userId, String username,
            LoginLogType logType, LoginResult loginResult) {
        // 插入登录日志
        LoginLog log = new LoginLog();
        log.setLogType(logType.getType());
        log.setUserId(userId);
        log.setUsername(username);
        log.setUserAgent(ServletUtils.getUserAgent());
        log.setUserIp(ServletUtils.getClientIP());
        log.setResult(loginResult.getValue());
        loginLogService.createLoginLog(log);
        // 更新最后登录时间
        if (userId != null && Objects.equals(LoginResult.SUCCESS.getValue(), loginResult.getValue())) {
            userService.updateUserLogin(userId, ServletUtils.getClientIP());
        }
    }

    private void createLogoutLog(Long userId, Integer logType) {
        LoginLog log = new LoginLog();
        log.setLogType(logType);
        log.setUserId(userId);
        log.setUsername(getUsername(userId));
        log.setUserAgent(ServletUtils.getUserAgent());
        log.setUserIp(ServletUtils.getClientIP());
        log.setResult(LoginResult.SUCCESS.getValue());
        loginLogService.createLoginLog(log);
    }

    private String getUsername(Long userId) {
        if (userId == null) {
            return null;
        }
        User user = userService.getUser(userId);
        return user != null ? user.getUsername() : null;
    }

}
