package com.future.framework.common.domain;

import com.future.framework.common.exception.CommonErrorCode;

import java.util.HashMap;

public class R<T> extends HashMap<String, Object> {

    private static final String KEY_CODE = "code";
    private static final String KEY_MESSAGE = "msg";
    private static final String KEY_DATA = "data";

    private static final String MESSAGE_OK = "操作成功";
    private static final String MESSAGE_FAIL = "操作失败";

    public static final int CODE_OK = 0;
    public static final int CODE_FAIL = -1;

    public static <T> R<T> ok() {
        return new R<T>().code(CODE_OK).message(MESSAGE_OK);
    }

    public static <T> R<T> ok(T data) {
        return new R<T>().code(CODE_OK).message(MESSAGE_OK).data(data);
    }

    public static <T> R<T> fail() {
        return fail(MESSAGE_FAIL);
    }

    public static <T> R<T> fail(int code, String message) {
        return new R<T>().code(code).message(message);
    }

    public static <T> R<T> fail(String message) {
        return new R<T>().code(CODE_FAIL).message(message);
    }

    public static <T> R<T> fail(CommonErrorCode errorCode) {
        return new R<T>().code(errorCode.getCode()).message(errorCode.getMessage());
    }

    private R() {
    }

    public R<T> code(int code) {
        put(KEY_CODE, code);
        return this;
    }

    public R<T> message(String message) {
        put(KEY_MESSAGE, message);
        return this;
    }

    public R<T> data(T data) {
        put(KEY_DATA, data);
        return this;
    }

}
