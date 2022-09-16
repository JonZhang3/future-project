package com.future.module.system.domain.query.auth;

import com.future.framework.common.domain.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AccessTokenPageQuery extends BaseQuery {

//    @ApiModelProperty(value = "用户编号", required = true, example = "666")
    private Long userId;

//    @ApiModelProperty(value = "用户类型", required = true, example = "2", notes = "参见 UserTypeEnum 枚举")
    private Integer userType;

//    @ApiModelProperty(value = "客户端编号", required = true, example = "2")
    private String clientId;

}
