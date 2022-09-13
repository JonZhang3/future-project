package com.future.framework.security.aop;

import com.future.framework.common.exception.ServiceException;
import com.future.framework.security.annotations.PreAuthenticated;
import com.future.framework.security.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static com.future.framework.common.exception.CommonErrorCode.UNAUTHORIZED;

/**
 * 处理用户未登录拦截的切面的 Bean
 */
@Aspect
@Component
@Slf4j
public class PreAuthenticatedAspect {

    @Around("@annotation(preAuthenticated)")
    public Object around(ProceedingJoinPoint joinPoint, PreAuthenticated preAuthenticated) throws Throwable {
        if (SecurityUtils.getLoginUser() == null) {
            throw new ServiceException(UNAUTHORIZED);
        }
        return joinPoint.proceed();
    }

}
