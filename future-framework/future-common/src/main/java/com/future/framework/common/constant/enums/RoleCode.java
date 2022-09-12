package com.future.framework.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色标识枚举
 */
@Getter
@AllArgsConstructor
public enum RoleCode {

    SUPER_ADMIN("super_admin", "超级管理员"),
    ;

    /**
     * 角色编码
     */
    private final String code;
    /**
     * 名字
     */
    private final String name;

    public static boolean isSuperAdmin(String code) {
        return SUPER_ADMIN.getCode().equals(code);
    }

}
