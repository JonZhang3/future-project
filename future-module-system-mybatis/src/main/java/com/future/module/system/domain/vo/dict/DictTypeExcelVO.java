package com.future.module.system.domain.vo.dict;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DictTypeExcelVO {

    @ExcelProperty("字典主键")
    private Long id;

    @ExcelProperty("字典名称")
    private String name;

    @ExcelProperty("字典类型")
    private String type;

    @ExcelProperty(value = "状态")
    private Integer status;
    
}
