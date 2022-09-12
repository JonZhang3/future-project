package com.future.framework.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通知类型
 *
 * @author JonZhang
 */
@Getter
@AllArgsConstructor
public enum NoticeType {

    NOTICE(1),
    ANNOUNCEMENT(2);

    /**
     * 类型
     */
    private final Integer value;

}
