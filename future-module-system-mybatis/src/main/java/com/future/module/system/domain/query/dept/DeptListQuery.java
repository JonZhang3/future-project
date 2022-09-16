package com.future.module.system.domain.query.dept;

import lombok.Data;

//@ApiModel("管理后台 - 部门列表 Request VO")
@Data
public class DeptListQuery {

//    @ApiModelProperty(value = "部门名称", example = "芋道", notes = "模糊匹配")
    private String name;

//    @ApiModelProperty(value = "展示状态", example = "1", notes = "参见 CommonStatusEnum 枚举类")
    private Integer status;
    
}
