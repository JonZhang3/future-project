package com.future.module.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.future.framework.mybatis.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * OAuth2 刷新令牌
 *
 * @author JonZhang
 */
@TableName(value = "system_refresh_token", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class RefreshToken extends BaseEntity {

    /**
     * 编号，数据库字典
     */
    private Long id;
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
