package com.future.module.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.future.framework.mybatis.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通知公告表
 *
 * @author JonZhang
 */
@TableName("system_notice")
@Data
@EqualsAndHashCode(callSuper = true)
public class Notice extends BaseEntity {

    /**
     * 公告ID
     */
    private Long id;
    /**
     * 公告标题
     */
    private String title;
    /**
     * 公告类型
     * <p>
     * 枚举 {@link com.future.framework.common.constant.enums.NoticeType}
     */
    private Integer type;
    /**
     * 公告内容
     */
    private String content;
    /**
     * 公告状态
     * <p>
     * 枚举 {@link com.future.framework.common.constant.enums.CommonStatus}
     */
    private Integer status;

}
