package com.future.module.system.domain.query.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

//@ApiModel("管理后台 - 用户创建 Request Query")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserCreateQuery extends UserBaseQuery {

//    @ApiModelProperty(value = "密码", required = true, example = "123456")
    @NotEmpty(message = "密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String password;

}
