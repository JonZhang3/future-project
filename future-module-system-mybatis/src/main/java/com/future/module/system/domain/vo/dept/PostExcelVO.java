package com.future.module.system.domain.vo.dept;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class PostExcelVO {

    @ExcelProperty("岗位序号")
    private Long id;

    @ExcelProperty("岗位编码")
    private String code;

    @ExcelProperty("岗位名称")
    private String name;

    @ExcelProperty("岗位排序")
    private Integer sort;

    @ExcelProperty(value = "状态")
    private String status;

}
