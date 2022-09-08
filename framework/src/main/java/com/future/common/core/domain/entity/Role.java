package com.future.common.core.domain.entity;

import com.future.common.constant.enums.State;
import com.future.common.core.domain.BaseEntity;
import com.future.framework.jpa.SnowflakeIdGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 角色信息
 *
 * @author JonZhang
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_role")
@org.hibernate.annotations.Table(appliesTo = "sys_role", comment = "角色表")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = SnowflakeIdGenerator.GENERATOR_NAME)
    @GenericGenerator(name = SnowflakeIdGenerator.GENERATOR_NAME, strategy = GENERATOR_STRATEGY)
    private Long id;
    
    @Comment("角色名称")
    @Column(nullable = false, length = 30, unique = true)
    private String name;

    @Comment("角色权限")
    @Column(nullable = false, length = 100)
    private String code;

    @Comment("数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
    @Column(columnDefinition = "tinyint(1) default 1")
    private Integer dataScope;

    @Comment("描述")
    @Column(length = 500)
    private String remark;

    @Comment("排序")
    @Column(columnDefinition = "int(4) NOT NULL DEFAULT 1")
    private Integer sortNum;

    @Comment("状态 1-正常 0-停用 -1-删除")
    @Column(columnDefinition = "tinyint(1) NOT NULL DEFAULT 1")
    private State state;

}
