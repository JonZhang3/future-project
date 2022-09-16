package com.future.module.system.domain.query.permission;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

//@ApiModel("管理后台 - 角色更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleUpdateQuery extends RoleBaseQuery {

//    @ApiModelProperty(value = "角色编号", required = true, example = "1024")
    @NotNull(message = "角色编号不能为空")
    private Long id;
    
}
