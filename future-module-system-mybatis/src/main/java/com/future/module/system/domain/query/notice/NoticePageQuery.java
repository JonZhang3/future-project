package com.future.module.system.domain.query.notice;

import com.future.framework.common.domain.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

//@ApiModel("管理后台 - 通知公告分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class NoticePageQuery extends BaseQuery {

//    @ApiModelProperty(value = "通知公告名称", example = "芋道", notes = "模糊匹配")
    private String title;

//    @ApiModelProperty(value = "展示状态", example = "1", notes = "参见 CommonStatusEnum 枚举类")
    private Integer status;
    
}
