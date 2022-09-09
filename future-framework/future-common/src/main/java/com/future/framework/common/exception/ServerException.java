package com.future.framework.common.exception;

/**
 * 服务器异常
 * 
 * @author JonZhang
 */
public class ServerException extends RuntimeException {

    private final Integer code;

    public ServerException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
