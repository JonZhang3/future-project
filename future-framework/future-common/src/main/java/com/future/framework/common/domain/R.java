package com.future.framework.common.domain;

import com.future.framework.common.exception.CommonErrorCode;

import java.util.HashMap;

public class R extends HashMap<String, Object> {

    private static final String KEY_CODE = "code";
    private static final String KEY_MESSAGE = "msg";
    private static final String KEY_DATA = "data";

    private static final String MESSAGE_OK = "操作成功";
    private static final String MESSAGE_FAIL = "操作失败";

    public static final int CODE_OK = 1;
    public static final int CODE_FAIL = -1;

    public static R ok() {
        return new R().code(CODE_OK).message(MESSAGE_OK);
    }

    public static R ok(Object data) {
        return ok().data(data);
    }

    public static R fail() {
        return fail(MESSAGE_FAIL);
    }

    public static R fail(int code, String message) {
        return new R().code(code).message(message);
    }

    public static R fail(String message) {
        return new R().code(CODE_FAIL).message(message);
    }

    public static R fail(CommonErrorCode errorCode) {
        return new R().code(errorCode.getCode()).message(errorCode.getMessage());
    }

    private R() {
    }

    public R code(int code) {
        put(KEY_CODE, code);
        return this;
    }

    public R message(String message) {
        put(KEY_MESSAGE, message);
        return this;
    }

    public R data(Object data) {
        put(KEY_DATA, data);
        return this;
    }

    public R addData(String key, Object data) {
        put(key, data);
        return this;
    }

}
