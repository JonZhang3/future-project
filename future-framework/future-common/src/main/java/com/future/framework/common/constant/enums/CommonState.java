package com.future.framework.common.constant.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonState {

    VALID(1),// 有效的
    INVALID(0),// 无效的/停用
    ;
    
    @JsonValue
    private final Integer value;

}
