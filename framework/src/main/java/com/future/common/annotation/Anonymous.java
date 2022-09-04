package com.future.common.annotation;

import java.lang.annotation.*;

/**
 * 匿名访问不需要权限注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Anonymous {
}
