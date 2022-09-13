package com.future.framework.security.domain;

import lombok.Data;

import java.util.List;

/**
 * OAuth2.0 访问令牌的校验 Response DTO
 *
 * @author JonZhang
 */
@Data
public class AccessTokenCheckResult {

    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户类型
     */
    private Integer userType;
    /**
     * 租户编号
     */
    private Long tenantId;
    /**
     * 授权范围的数组
     */
    private List<String> scopes;
    
}
