package com.future.module.system.domain.vo.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//@ApiModel("管理后台 - 登录 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthLoginVO {

//    @ApiModelProperty(value = "用户编号", required = true, example = "1024")
    private Long userId;

//    @ApiModelProperty(value = "访问令牌", required = true, example = "happy")
    private String accessToken;

//    @ApiModelProperty(value = "刷新令牌", required = true, example = "nice")
    private String refreshToken;

//    @ApiModelProperty(value = "过期时间", required = true)
    private Date expiresTime;

}
