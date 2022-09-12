package com.future.module.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.future.framework.mybatis.domain.BaseEntity;
import com.future.framework.mybatis.type.JsonLongSetTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * 角色
 *
 * @author JonZhang
 */
@TableName(value = "system_role", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {

    /**
     * 角色ID
     */
    @TableId
    private Long id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色标识
     * <p>
     * 枚举
     */
    private String code;
    /**
     * 角色排序
     */
    private Integer sort;
    /**
     * 角色状态
     * <p>
     * 枚举 {@link com.future.framework.common.constant.enums.CommonStatus}
     */
    private Integer status;
    /**
     * 角色类型
     * 枚举 {@link com.future.framework.common.constant.enums.RoleType}
     */
    private Integer type;
    /**
     * 备注
     */
    private String remark;

    /**
     * 数据范围
     * 枚举 {@link com.future.framework.common.constant.enums.DataScope}
     */
    private Integer dataScope;
    /**
     * 数据范围(指定部门数组)
     * 适用于 {@link #dataScope} 的值为 {@link com.future.framework.common.constant.enums.DataScope#DEPT_CUSTOM} 时
     */
    @TableField(typeHandler = JsonLongSetTypeHandler.class)
    private Set<Long> dataScopeDeptIds;

}
