package com.future.module.system.domain.vo.dict;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DictDataExcelVO {

    @ExcelProperty("字典编码")
    private Long id;

    @ExcelProperty("字典排序")
    private Integer sort;

    @ExcelProperty("字典标签")
    private String label;

    @ExcelProperty("字典键值")
    private String value;

    @ExcelProperty("字典类型")
    private String dictType;

    @ExcelProperty(value = "状态")
    private Integer status;
    
}
