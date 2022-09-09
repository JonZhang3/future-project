package com.future.framework.common.exception;

/**
 * 业务逻辑异常
 * 
 * @author JonZhang
 */
public final class ServiceException extends RuntimeException {

    private Integer code;

    public ServiceException(String message) {
        super(message, null, false, false);
    }

    public ServiceException(String message, Integer code) {
        this(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
