package com.future.framework.config.security;

import com.future.framework.web.service.TokenService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登出处理类
 * 
 * @author JonZhang
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    
    @Resource(type = TokenService.class)
    private TokenService tokenService;
    
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {

    }
}
