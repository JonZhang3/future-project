package com.future.common.core.domain.entity;

import com.future.common.constant.enums.State;
import com.future.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_post")
@org.hibernate.annotations.Table(appliesTo = "sys_post", comment = "岗位信息表")
public class Post extends BaseEntity {

    private static final long serialVersionUID = 1L;
    
    @Comment("岗位编码")
    @Column(length = 64)
    private String code;

    @Comment("岗位名称")
    @Column(length = 64)
    private String name;

    @Comment("排序")
    @Column(columnDefinition = "int(4) NOT NULL DEFAULT 1")
    private Integer sortNum;

    @Comment("状态 1-正常 0-停用 -1-删除")
    @Column(columnDefinition = "tinyint(1) NOT NULL DEFAULT 1")
    private State state;

}
