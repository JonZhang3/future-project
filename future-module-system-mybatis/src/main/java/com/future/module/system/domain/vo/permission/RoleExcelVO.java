package com.future.module.system.domain.vo.permission;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class RoleExcelVO {

    @ExcelProperty("角色序号")
    private Long id;

    @ExcelProperty("角色名称")
    private String name;

    @ExcelProperty("角色标志")
    private String code;

    @ExcelProperty("角色排序")
    private Integer sort;

    @ExcelProperty("数据范围")
    private Integer dataScope;

    @ExcelProperty(value = "角色状态")
    private String status;

}
