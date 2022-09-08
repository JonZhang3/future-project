package com.future.framework.web;

import com.future.common.core.domain.R;
import com.future.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 *
 * @author JonZhang
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 权限校验异常
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<R> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        String url = request.getRequestURI();
        log.error("request url [{}], access demoed: {}", url, e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(R.fail("暂无权限"));
    }

    // 请求方式不支持
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception,
                                                          HttpServletRequest request) {
        String url = request.getRequestURI();
        log.error("request url [{}], not support [{}] method", url, exception.getMethod().toUpperCase());
        return R.fail(exception.getMessage());
    }

    // 业务异常
    @ExceptionHandler(BusinessException.class)
    public Object handleBusinessException(BusinessException e) {
        if (e.getHttpStatus() != null) {
            return ResponseEntity.status(e.getHttpStatus()).body(R.fail(e.getMessage()));
        }
        return R.fail(e.getMessage());
    }

    // 自定义验证异常
    @ExceptionHandler(BindException.class)
    public R handleBindException(BindException e) {
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return R.fail(message);
    }

    // 自定义验证异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String message;
        if (fieldError != null) {
            message = fieldError.getDefaultMessage();
        } else {
            message = "参数验证错误";
        }
        return R.fail(message);
    }

    // 未知的运行时异常
    @ExceptionHandler(RuntimeException.class)
    public R handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String url = request.getRequestURI();
        log.error("request url [{}], unknown exception", url, e);
        return R.fail("未知错误: " + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e, HttpServletRequest request) {
        String url = request.getRequestURI();
        log.error("request url [{}] error", url, e);
        return R.fail("系统异常: " + e.getMessage());
    }

}
