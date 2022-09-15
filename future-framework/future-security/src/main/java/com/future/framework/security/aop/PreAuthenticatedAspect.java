package com.future.framework.security.aop;

import static com.future.framework.common.exception.CommonErrorCode.UNAUTHORIZED;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.future.framework.common.exception.ServiceException;
import com.future.framework.security.annotations.PreAuthenticated;
import com.future.framework.security.util.SecurityUtils;

/**
 * 处理用户未登录拦截的切面的 Bean
 */
@Aspect
@Component
public class PreAuthenticatedAspect {

    @Around("@annotation(preAuthenticated)")
    public Object around(ProceedingJoinPoint joinPoint, PreAuthenticated preAuthenticated) throws Throwable {
        if (SecurityUtils.getLoginUser() == null) {
            throw new ServiceException(UNAUTHORIZED);
        }
        return joinPoint.proceed();
    }

}
