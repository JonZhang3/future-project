package com.future.module.system.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Menu 编号枚举
 *
 * @author JonZhang
 */
@Getter
@AllArgsConstructor
public enum MenuId {

    /**
     * 根节点
     */
    ROOT(0L);

    private final Long id;

}
