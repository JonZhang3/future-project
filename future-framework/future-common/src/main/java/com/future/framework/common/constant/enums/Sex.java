package com.future.framework.common.constant.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别的枚举值
 *
 * @author JonZhang
 */
@Getter
@AllArgsConstructor
public enum Sex {

    /**
     * 男
     */
    MALE(1),
    /**
     * 女
     */
    FEMALE(2),
    /**
     * 未知
     */
    UNKNOWN(3);

    /**
     * 性别
     */
    @JsonValue
    private final Integer value;

}
