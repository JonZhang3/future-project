package com.future.module.system.domain.vo.dict;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class DictDataBaseVO {

//    @ApiModelProperty(value = "显示顺序不能为空", required = true, example = "1024")
    @NotNull(message = "显示顺序不能为空")
    private Integer sort;

//    @ApiModelProperty(value = "字典标签", required = true, example = "芋道")
    @NotBlank(message = "字典标签不能为空")
    @Size(max = 100, message = "字典标签长度不能超过100个字符")
    private String label;

//    @ApiModelProperty(value = "字典值", required = true, example = "iocoder")
    @NotBlank(message = "字典键值不能为空")
    @Size(max = 100, message = "字典键值长度不能超过100个字符")
    private String value;

//    @ApiModelProperty(value = "字典类型", required = true, example = "sys_common_sex")
    @NotBlank(message = "字典类型不能为空")
    @Size(max = 100, message = "字典类型长度不能超过100个字符")
    private String dictType;

//    @ApiModelProperty(value = "状态", required = true, example = "1", notes = "见 CommonStatusEnum 枚举")
    @NotNull(message = "状态不能为空")
//    @InEnum(value = CommonStatusEnum.class, message = "修改状态必须是 {value}")
    private Integer status;

//    @ApiModelProperty(value = "颜色类型", example = "default", notes = "default、primary、success、info、warning、danger")
    private String colorType;
//    @ApiModelProperty(value = "css 样式", example = "btn-visible")
    private String cssClass;

//    @ApiModelProperty(value = "备注", example = "我是一个角色")
    private String remark;
    
}
