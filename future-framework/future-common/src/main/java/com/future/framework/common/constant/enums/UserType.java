package com.future.framework.common.constant.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType {

    MEMBER(1),// 普通用户
    ADMIN(2),// 管理员
    ;

    @JsonValue
    private final Integer value;

}
