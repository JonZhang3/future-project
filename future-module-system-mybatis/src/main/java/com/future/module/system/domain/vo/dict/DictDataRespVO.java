package com.future.module.system.domain.vo.dict;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

//@ApiModel("管理后台 - 字典数据信息 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DictDataRespVO extends DictDataBaseVO {

//    @ApiModelProperty(value = "字典数据编号", required = true, example = "1024")
    private Long id;

//    @ApiModelProperty(value = "创建时间", required = true, example = "时间戳格式")
    private Date createTime;

}
