package com.future.system.domain.entity;

import com.future.common.constant.enums.OperatingState;
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
@Table(name = "sys_logininfo")
@org.hibernate.annotations.Table(appliesTo = "sys_logininfo", comment = "系统访问日志")
public class LoginInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("用户ID")
    private Long userId;

    @Transient
    private String username;

    @Comment("登录IP地址")
    @Column(length = 128)
    private String ip;

    @Comment("浏览器类型")
    @Column(length = 50)
    private String browser;

    @Comment("操作系统")
    @Column(length = 50)
    private String os;

    @Comment("登录状态 1成功 0失败")
    @Column(columnDefinition = "tinyint(1) NOT NULL DEFAULT 1")
    private OperatingState state = OperatingState.SUCCESS;

    @Comment("提示消息")
    private String message;

}
