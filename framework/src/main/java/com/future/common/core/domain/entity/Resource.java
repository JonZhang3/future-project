package com.future.common.core.domain.entity;

import com.future.common.constant.enums.ResourceType;
import com.future.common.constant.enums.State;
import com.future.common.core.domain.BaseEntity;
import com.future.framework.jpa.SnowflakeIdGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_resource")
@org.hibernate.annotations.Table(appliesTo = "sys_resource", comment = "资源表")
public class Resource extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = SnowflakeIdGenerator.GENERATOR_NAME)
    @GenericGenerator(name = SnowflakeIdGenerator.GENERATOR_NAME, strategy = GENERATOR_STRATEGY)
    private Long id;
    
    @Comment("资源名称")
    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @Comment("父资源ID")
    private Long parentId;

    @Comment("路由地址")
    @Column(length = 200)
    private String path;

    @Comment("组件路径")
    private String component;

    @Comment("路由参数")
    private String query;

    @Comment("权限标识")
    @Column(length = 100)
    private String perms;

    @Comment("图标")
    @Column(length = 100)
    private String icon;

    @Comment("描述")
    @Column(length = 500)
    private String remark;

    @Comment("是否为外链 1是 0否")
    @Column(columnDefinition = "tinyint(1) NOT NULL DEFAULT 0")
    private Integer isFrame;

    @Comment("资源类型")
    @Column(columnDefinition = "tinyint(1) NOT NULL DEFAULT 1")
    private ResourceType type = ResourceType.MENU;

    @Comment("状态 1-正常 0-停用 -1-删除")
    @Column(columnDefinition = "tinyint(1) NOT NULL DEFAULT 1")
    private State state;

    @Comment("排序")
    @Column(columnDefinition = "int(4) NOT NULL DEFAULT 1")
    private Integer sortNum = 1;

    @Transient
    private List<Resource> children;

}
