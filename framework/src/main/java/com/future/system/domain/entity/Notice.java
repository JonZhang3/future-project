package com.future.system.domain.entity;

import com.future.common.constant.enums.State;
import com.future.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_notice")
@org.hibernate.annotations.Table(appliesTo = "sys_notice", comment = "通知公告")
public class Notice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Comment("公告标题")
    @Column(length = 50, nullable = false)
    private String title;
    
    @Comment("公告类型 1通知 2公告")
    @Column(columnDefinition = "tinyint(1) NOT NULL")
    private Integer type;
    
    @Comment("公告内容")
    @Column(columnDefinition = "longtext")
    private String content;
    
    @Comment("公告状态 1正常 0关闭")
    @Column(columnDefinition = "tinyint(1) NOT NULL DEFAULT 1")
    private State state;
    
    @Comment("备注")
    @Column(length = 500)
    private String remark;

}
