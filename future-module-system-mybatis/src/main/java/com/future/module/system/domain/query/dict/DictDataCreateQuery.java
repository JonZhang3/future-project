package com.future.module.system.domain.query.dict;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel("管理后台 - 字典数据创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class DictDataCreateQuery extends DictDataBaseQuery {
}
