package com.future.module.system.domain.query.notice;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class NoticeBaseQuery {

//    @ApiModelProperty(value = "公告标题", required = true, example = "小博主")
    @NotBlank(message = "公告标题不能为空")
    @Size(max = 50, message = "公告标题不能超过50个字符")
    private String title;

//    @ApiModelProperty(value = "公告类型", required = true, example = "小博主")
    @NotNull(message = "公告类型不能为空")
    private Integer type;

//    @ApiModelProperty(value = "公告内容", required = true, example = "半生编码")
    private String content;

//    @ApiModelProperty(value = "状态", required = true, example = "1", notes = "参见 CommonStatusEnum 枚举类")
    private Integer status;
    
}
