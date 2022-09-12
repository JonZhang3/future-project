package com.future.module.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.future.framework.mybatis.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 岗位表
 *
 * @author JonZhang
 */
@TableName("system_post")
@Data
@EqualsAndHashCode(callSuper = true)
public class Post extends BaseEntity {

    /**
     * 岗位序号
     */
    @TableId
    private Long id;
    /**
     * 岗位名称
     */
    private String name;
    /**
     * 岗位编码
     */
    private String code;
    /**
     * 岗位排序
     */
    private Integer sort;
    /**
     * 状态
     * 枚举 {@link com.future.framework.common.constant.enums.CommonStatus}
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;

}
