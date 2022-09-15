package com.future.module.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.future.framework.mybatis.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 登录日志表
 * 包括登录和登出两种行为
 *
 * @author JonZhang
 */
@TableName("system_login_log")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LoginLog extends BaseEntity {

    /**
     * 日志主键
     */
    private Long id;
    /**
     * 日志类型
     * 枚举 {@link com.future.framework.common.constant.enums.LoginLogType}
     */
    private Integer logType;
    /**
     * 链路追踪编号
     */
    private String traceId;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户账号
     * 冗余，因为账号可以变更
     */
    private String username;
    /**
     * 登录结果
     * 枚举 {@link com.future.framework.common.constant.enums.LoginResult}
     */
    private Integer result;
    /**
     * 用户 IP
     */
    private String userIp;
    /**
     * 浏览器 UA
     */
    private String userAgent;


}
