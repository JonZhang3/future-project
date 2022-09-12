package com.future.framework.common.exception;

/**
 * 工具类异常
 * 
 * @author JonZhang
 */
public class UtilityException extends RuntimeException {

    public UtilityException() {
    }

    public UtilityException(String message) {
        super(message);
    }

    public UtilityException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilityException(Throwable cause) {
        super(cause);
    }

    public UtilityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
