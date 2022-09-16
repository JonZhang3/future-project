package com.future.module.system.domain.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@ApiModel("用户精简信息 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSimpleRespVO {

//    @ApiModelProperty(value = "用户编号", required = true, example = "1024")
    private Long id;

//    @ApiModelProperty(value = "用户昵称", required = true, example = "Jon")
    private String nickname;

}