package com.future.framework.security.filter;

import com.future.framework.common.domain.R;
import com.future.framework.common.exception.ServiceException;
import com.future.framework.common.utils.ServletUtils;
import com.future.framework.common.utils.StringUtils;
import com.future.framework.security.config.SecurityProperties;
import com.future.framework.security.domain.AccessTokenCheckResult;
import com.future.framework.security.domain.LoginUser;
import com.future.framework.security.service.TokenService;
import com.future.framework.security.util.SecurityUtils;
import com.future.framework.web.handler.GlobalExceptionHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token 过滤器，验证 token 的有效性
 * 验证通过后，获得 {@link LoginUser} 信息，并加入到 Spring Security 上下文
 *
 * @author JonZhang
 */
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Resource(type = SecurityProperties.class)
    private SecurityProperties securityProperties;

    @Resource(type = GlobalExceptionHandler.class)
    private GlobalExceptionHandler globalExceptionHandler;

    @Resource(type = TokenService.class)
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain chain) throws ServletException, IOException {
        // 从请求头中取得 Token
        String token = SecurityUtils.obtainAuthorization(request, securityProperties.getTokenHeader());
        if (StringUtils.isNotEmpty(token)) {
            // Integer userType = WebUtils.getLoginUserType(request);
            try {
                // 1.1 基于 token 构建登录用户
                LoginUser loginUser = buildLoginUserByToken(token);
                // 可以在这里手动设置LoginUser，方便日常开发调试
                if (loginUser == null) {
                    
                }
                // 2. 设置当前用户
                if (loginUser != null) {
                    SecurityUtils.setLoginUser(loginUser, request);
                }
            } catch (Throwable ex) {
                R result = globalExceptionHandler.allExceptionHandler(request, ex);
                ServletUtils.writeJSON(response, result);
                return;
            }
        }
        // 继续过滤链
        chain.doFilter(request, response);
    }

    private LoginUser buildLoginUserByToken(String token) {
        try {
            AccessTokenCheckResult accessToken = tokenService.checkAccessToken(token);
            if (accessToken == null) {
                return null;
            }
            // 构建登录用户
            return new LoginUser().setId(accessToken.getUserId())
                .setUserType(accessToken.getUserType())
                .setScopes(accessToken.getScopes());
        } catch (ServiceException serviceException) {
            // 校验 Token 不通过时，考虑到一些接口是无需登录的，所以直接返回 null 即可
            return null;
        }
    }

}
