package com.future.common.core.domain.entity;

import lombok.Data;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "sys_user_role")
@org.hibernate.annotations.Table(appliesTo = "sys_user_role", comment = "用户和角色关联表")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Comment("用户ID")
    private Long userId;
    
    @Id
    @Comment("角色ID")
    private Long roleId;

}
