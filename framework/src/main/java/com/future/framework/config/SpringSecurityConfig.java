package com.future.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.Resource;

/**
 * Spring Security配置
 *
 * @author JonZhang
 */
@Configuration
public class SpringSecurityConfig {

    @Resource(type = UserDetailsService.class)
    private UserDetailsService userDetailsService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        return http.build();
    }
    
}
