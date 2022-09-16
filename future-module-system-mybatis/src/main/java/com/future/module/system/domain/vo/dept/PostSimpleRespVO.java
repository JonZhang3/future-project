package com.future.module.system.domain.vo.dept;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@ApiModel("管理后台 - 岗位精简信息 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSimpleRespVO {

//    @ApiModelProperty(value = "岗位编号", required = true, example = "1024")
    private Long id;

//    @ApiModelProperty(value = "岗位名称", required = true, example = "芋道")
    private String name;

}
