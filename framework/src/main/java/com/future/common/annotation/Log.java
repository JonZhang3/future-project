package com.future.common.annotation;

import com.future.common.constant.enums.BusinessType;
import com.future.common.constant.enums.OperatorType;

import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @author JonZhang
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 功能名称
     */
    String title() default "";

    /**
     * 业务类型
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类型
     */
    OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存请求参数
     */
    boolean saveRequestData() default false;

    /**
     * 是否保存响应参数
     */
    boolean saveResponseData() default false;

}
