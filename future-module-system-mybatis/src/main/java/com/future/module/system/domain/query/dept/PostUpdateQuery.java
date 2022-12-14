package com.future.module.system.domain.query.dept;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

//@ApiModel("管理后台 - 岗位更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class PostUpdateQuery extends PostBaseQuery {

//    @ApiModelProperty(value = "岗位编号", required = true, example = "1024")
    @NotNull(message = "岗位编号不能为空")
    private Long id;
    
}
