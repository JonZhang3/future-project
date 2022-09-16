package com.future.module.system.domain.query.dict;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.future.framework.common.utils.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

//@ApiModel("管理后台 - 字典类型分页列表 Request VO")
@Data
public class DictTypeExportQuery {

//    @ApiModelProperty(value = "字典类型名称", example = "芋道", notes = "模糊匹配")
    private String name;

//    @ApiModelProperty(value = "字典类型", example = "sys_common_sex", notes = "模糊匹配")
    private String type;

//    @ApiModelProperty(value = "展示状态", example = "1", notes = "参见 CommonStatusEnum 枚举类")
    private Integer status;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
//    @ApiModelProperty(value = "创建时间")
    private Date[] createTime;

}
