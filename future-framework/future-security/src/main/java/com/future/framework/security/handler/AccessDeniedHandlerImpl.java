package com.future.framework.security.handler;

import com.future.framework.common.domain.R;
import com.future.framework.common.exception.CommonErrorCode;
import com.future.framework.common.utils.ServletUtils;
import com.future.framework.security.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.future.framework.common.exception.CommonErrorCode.FORBIDDEN;

/**
 * 权限不够处理器
 * 访问一个需要认证的 URL 资源，已经认证（登录）但是没有权限的情况下，返回 {@link CommonErrorCode#FORBIDDEN} 错误码。
 *
 * @author JonZhang
 */
@Component
@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException e) {
        // 打印 warn 的原因是，不定期合并 warn，看看有没恶意破坏
        log.info("[commence][访问 URL({}) 时，用户({}) 权限不够]", request.getRequestURI(),
            SecurityUtils.getLoginUserId(), e);
        // 返回 403
        ServletUtils.writeJSON(response, R.fail(FORBIDDEN));
    }
}
