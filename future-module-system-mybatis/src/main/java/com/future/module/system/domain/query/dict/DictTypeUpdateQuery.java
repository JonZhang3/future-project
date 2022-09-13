package com.future.module.system.domain.query.dict;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@ApiModel("管理后台 - 字典类型更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class DictTypeUpdateQuery extends DictTypeBaseQuery {

    @ApiModelProperty(value = "字典类型编号", required = true, example = "1024")
    @NotNull(message = "字典类型编号不能为空")
    private Long id;
    
}