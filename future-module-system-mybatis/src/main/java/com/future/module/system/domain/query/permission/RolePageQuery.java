package com.future.module.system.domain.query.permission;

import com.future.framework.common.domain.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.future.framework.common.utils.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

//@ApiModel("管理后台 - 角色分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class RolePageQuery extends BaseQuery {

//    @ApiModelProperty(value = "角色名称", example = "芋道", notes = "模糊匹配")
    private String name;

//    @ApiModelProperty(value = "角色标识", example = "yudao", notes = "模糊匹配")
    private String code;

//    @ApiModelProperty(value = "展示状态", example = "1", notes = "参见 CommonStatusEnum 枚举类")
    private Integer status;

//    @ApiModelProperty(value = "创建时间", example = "[2022-07-01 00:00:00,2022-07-01 23:59:59]")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

}
