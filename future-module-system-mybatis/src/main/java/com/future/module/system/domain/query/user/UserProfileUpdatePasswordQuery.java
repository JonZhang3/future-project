package com.future.module.system.domain.query.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

//@ApiModel("管理后台 - 用户个人中心更新密码 Request Query")
@Data
public class UserProfileUpdatePasswordQuery {

//    @ApiModelProperty(value = "旧密码", required = true, example = "123456")
    @NotEmpty(message = "旧密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String oldPassword;

//    @ApiModelProperty(value = "新密码", required = true, example = "654321")
    @NotEmpty(message = "新密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String newPassword;
    
}
