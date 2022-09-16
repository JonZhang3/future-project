package com.future.module.system.domain.query.permission;

import lombok.Data;

//@ApiModel("管理后台 - 菜单列表 Request VO")
@Data
public class MenuListQuery {

//    @ApiModelProperty(value = "菜单名称", example = "芋道", notes = "模糊匹配")
    private String name;

//    @ApiModelProperty(value = "展示状态", example = "1", notes = "参见 CommonStatus 枚举类")
    private Integer status;

}
