package com.future.module.system.domain.query.dept;

import com.future.framework.common.domain.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

//@ApiModel("管理后台 - 岗位分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class PostPageQuery extends BaseQuery {

//    @ApiModelProperty(value = "岗位编码", example = "yudao", notes = "模糊匹配")
    private String code;

//    @ApiModelProperty(value = "岗位名称", example = "芋道", notes = "模糊匹配")
    private String name;

//    @ApiModelProperty(value = "展示状态", example = "1", notes = "参见 CommonStatus 枚举类")
    private Integer status;
    
}
