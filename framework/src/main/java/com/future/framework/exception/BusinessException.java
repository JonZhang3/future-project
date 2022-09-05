package com.future.framework.exception;

/**
 * 业务异常，该异常不会跟踪堆栈信息
 * 
 * @author JonZhang
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message, null, false, false);
    }

}
