package com.future.framework.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;

/**
 * Spring Security配置
 * 
 * @author JonZhang
 */
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Resource(type = UserDetailsService.class)
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()// CSRF禁用，因为不使用session
        ;
    }
}
