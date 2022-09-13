package com.future.framework.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    SUCCESS(1, "成功"),
    BAD_REQUEST(400, "请求参数不正确"),
    UNAUTHORIZED(401, "未登录"),
    FORBIDDEN(403, "没有权限"),
    NOT_FOUND(404, "未找到"),
    METHOD_NOT_ALLOWED(405, "请求方法不正确"),
    LOCKED(423, "请求失败，请稍后重试"),
    TOO_MANY_REQUESTS(429, "请求过于频繁，请稍后重试"),
    INTERNAL_SERVER_ERROR(500, "系统异常"),

    ;

    private final Integer code;
    private final String message;

}
