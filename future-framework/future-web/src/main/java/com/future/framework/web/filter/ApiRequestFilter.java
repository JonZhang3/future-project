package com.future.framework.web.filter;

import com.future.framework.common.utils.StringUtils;
import com.future.framework.web.config.WebProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * 过滤 /admin-api、/app-api 等 API 请求的过滤器
 *
 * @author JonZhang
 */
@RequiredArgsConstructor
public abstract class ApiRequestFilter extends OncePerRequestFilter {

    protected final WebProperties webProperties;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !StringUtils.startsWithAny(request.getRequestURI(),
            webProperties.getAdminApi().getPrefix(),
            webProperties.getAppApi().getPrefix());
    }
}
