package com.future.module.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.future.framework.mybatis.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * OAuth2 访问令牌 DO
 * <p>
 * 如下字段，暂时未使用，暂时不支持：
 * user_name、authentication（用户信息）
 *
 * @author JonZhang
 */
@TableName(value = "system_access_token", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class AccessToken extends BaseEntity {

    /**
     * 编号，数据库递增
     */
    @TableId
    private Long id;
    /**
     * 访问令牌
     */
    private String accessToken;
    /**
     * 刷新令牌
     */
    private String refreshToken;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户类型
     * <p>
     * 枚举 {@link com.future.framework.common.constant.enums.UserType}
     */
    private Integer userType;
    /**
     * 客户端编号
     */
    private String clientId;
    /**
     * 授权范围
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> scopes;
    /**
     * 过期时间
     */
    private Date expiresTime;

}
