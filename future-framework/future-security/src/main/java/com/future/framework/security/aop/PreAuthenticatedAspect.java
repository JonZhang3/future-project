package com.future.framework.security.aop;

import com.future.framework.common.exception.ServiceException;
import com.future.framework.security.annotations.PreAuthenticated;
import com.future.framework.security.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import static com.future.framework.common.exception.CommonErrorCode.UNAUTHORIZED;

@Aspect
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