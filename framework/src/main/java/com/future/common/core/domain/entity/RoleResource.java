package com.future.common.core.domain.entity;

import lombok.Data;
import org.hibernate.annotations.Comment;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "sys_role_resource")
@org.hibernate.annotations.Table(appliesTo = "sys_role_resource", comment = "角色和资源关联表")
public class RoleResource implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Comment("角色ID")
    @Id
    private Long roleId;
    
    @Comment("资源ID")
    @Id
    private Long resourceId;
    
}
