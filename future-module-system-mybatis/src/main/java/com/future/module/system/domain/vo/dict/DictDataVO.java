package com.future.module.system.domain.vo.dict;

import java.util.Date;

import com.future.module.system.domain.query.dict.DictDataBaseQuery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ApiModel("管理后台 - 字典数据信息 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DictDataVO extends DictDataBaseQuery {
    
    @ApiModelProperty(value = "字典数据编号", required = true, example = "1024")
    private Long id;

    @ApiModelProperty(value = "创建时间", required = true, example = "时间戳格式")
    private Date createTime;

}
