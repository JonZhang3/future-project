package com.future.framework.jpa;

import org.apache.catalina.security.SecurityUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * 自定义auditor获取方式，用于 @LastModifiedBy 和 @CreatedBy 注解
 *
 * @author JonZhang
 * @see org.springframework.data.annotation.LastModifiedBy
 * @see org.springframework.data.annotation.CreatedBy
 */
public class CustomAuditorAware implements AuditorAware<String> {

    @Override
    public @NotNull Optional<String> getCurrentAuditor() {
        // TODO
        return Optional.empty();
    }

}
