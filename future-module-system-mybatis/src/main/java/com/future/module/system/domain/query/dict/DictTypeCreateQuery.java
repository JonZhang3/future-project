package com.future.module.system.domain.query.dict;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//@ApiModel("管理后台 - 字典类型创建 Request Query")
@Data
@EqualsAndHashCode(callSuper = true)
public class DictTypeCreateQuery extends DictTypeBaseQuery {

//    @ApiModelProperty(value = "字典类型", required = true, example = "sys_common_sex")
    @NotNull(message = "字典类型不能为空")
    @Size(max = 100, message = "字典类型类型长度不能超过100个字符")
    private String type;

}
