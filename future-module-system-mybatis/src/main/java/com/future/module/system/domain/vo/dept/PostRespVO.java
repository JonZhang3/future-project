package com.future.module.system.domain.vo.dept;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

//@ApiModel("管理后台 - 岗位信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class PostRespVO extends PostBaseVO {

//    @ApiModelProperty(value = "岗位序号", required = true, example = "1024")
    private Long id;

//    @ApiModelProperty(value = "创建时间", required = true, example = "时间戳格式")
    private Date createTime;
    
}
