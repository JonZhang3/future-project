package com.future.framework.security.handler;

import com.future.framework.common.domain.R;
import com.future.framework.common.exception.CommonErrorCode;
import com.future.framework.common.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.future.framework.common.exception.CommonErrorCode.UNAUTHORIZED;

/**
 * 认证失败处理类 Bean
 * 访问一个需要认证的 URL 资源，但是此时自己尚未认证（登录）的情况下，
 * 返回 {@link CommonErrorCode#UNAUTHORIZED} 错误码，从而使前端重定向到登录页
 *
 * @author JonZhang
 */
@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) {
        log.info("[commence][访问 URL({}) 时，没有登录]", request.getRequestURI(), e);
        // 返回 401
        ServletUtils.writeJSON(response, R.fail(UNAUTHORIZED));
    }
}
