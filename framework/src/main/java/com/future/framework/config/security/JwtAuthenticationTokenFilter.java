package com.future.framework.config.security;

import com.future.common.core.domain.model.LoginUser;
import com.future.common.utils.SecurityUtils;
import com.future.framework.web.service.TokenService;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    
    @Resource(type = TokenService.class)
    private TokenService tokenService;
    
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, 
                                    @NotNull HttpServletResponse response, 
                                    @NotNull FilterChain chain) throws ServletException, IOException {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if(loginUser != null && SecurityUtils.getAuthentication() == null) {
            
            UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }
}
