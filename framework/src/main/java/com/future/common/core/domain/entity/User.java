package com.future.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.future.common.constant.enums.Sex;
import com.future.common.constant.enums.UserState;
import com.future.common.constant.enums.UserType;
import com.future.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 用户信息
 *
 * @author JonZhang
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_user")
@org.hibernate.annotations.Table(appliesTo = "sys_user", comment = "用户表")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Comment("用户名")
    @Column(unique = true, nullable = false, length = 64)
    private String username;

    @Comment("用户昵称")
    private String nickname;

    @JsonIgnore
    @Comment("密码")
    @Column(nullable = false, length = 64)
    private String password;

    @Comment("所属部门ID")
    private Long deptId;

    @Comment("随机盐")
    private String salt;

    @Comment("邮箱")
    @Column(length = 256)
    private String email;

    @Comment("手机号码")
    @Column(length = 20)
    private String phone;

    @Comment("性别")
    @Column(columnDefinition = "tinyint(1) NULL DEFAULT NULL")
    private Sex sex;

    @Comment("头像")
    @Column(length = 256)
    private String avatar;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Comment("上次登录时间")
    private LocalDateTime lastLoginTime;

    @Comment("用户状态, 1-正常 0-删除 -1-锁定")
    @Column(columnDefinition = "int(2) NOT NULL DEFAULT 1")
    private UserState state = UserState.VALID;
    
    @Comment("用户类型")
    @Column(columnDefinition = "tinyint(1) NOT NULL DEFAULT 1")
    private UserType userType = UserType.NORMAL;
    
}
