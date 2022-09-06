package com.future.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 业务异常，该异常不会跟踪堆栈信息
 * 
 * @author JonZhang
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    private HttpStatus httpStatus = HttpStatus.OK;
    
    public BusinessException(String message) {
        super(message, null, false, false);
    }

    public BusinessException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
