package com.future.module.system.domain.query.permission;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel("管理后台 - 角色创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleCreateQuery extends RoleBaseQuery {
}