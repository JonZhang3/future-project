package com.future.module.system.domain.vo.permission;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

//@ApiModel("管理后台 - 赋予用户角色 Request VO")
@Data
public class PermissionAssignUserRoleReqVO {

//    @ApiModelProperty(value = "用户编号", required = true, example = "1")
    @NotNull(message = "用户编号不能为空")
    private Long userId;

//    @ApiModelProperty(value = "角色编号列表", example = "1,3,5")
    private Set<Long> roleIds = Collections.emptySet(); // 兜底
    
}
