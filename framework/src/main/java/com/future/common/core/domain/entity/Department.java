package com.future.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.future.common.constant.enums.State;
import com.future.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_dept")
@org.hibernate.annotations.Table(appliesTo = "sys_dept", comment = "部门表")
public class Department extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Comment("父部门ID")
    private Long parentId;

    @Comment("部门名称")
    @Column(length = 30, nullable = false, unique = true)
    private String name;

    @Comment("组级列表")
    @Column(length = 50)
    private String ancestors;

    @Comment("负责人")
    @Column(length = 20)
    private String leader;

    @Comment("联系电话")
    @Column(length = 11)
    private String phone;

    @Comment("邮箱")
    @Column(length = 50)
    private String email;

    @Comment("状态 1-正常 0-停用 -1-删除")
    @Column(columnDefinition = "tinyint(1) NOT NULL DEFAULT 1")
    private State state = State.VALID;

    @Comment("排序")
    @Column(columnDefinition = "int(4) NOT NULL DEFAULT 1")
    private Integer sortNum = 1;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Transient
    private List<Department> children;
    
}
