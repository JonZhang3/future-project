package com.future.module.admin.controller;

import com.future.framework.common.annotations.OperateLog;
import com.future.framework.common.domain.R;
import com.future.framework.common.utils.ExcelUtils;
import com.future.module.system.domain.convert.DictTypeConvert;
import com.future.module.system.domain.entity.DictType;
import com.future.module.system.domain.query.dict.DictTypeCreateQuery;
import com.future.module.system.domain.query.dict.DictTypeExportQuery;
import com.future.module.system.domain.query.dict.DictTypePageQuery;
import com.future.module.system.domain.query.dict.DictTypeUpdateQuery;
import com.future.module.system.domain.vo.dict.DictTypeExcelVO;
import com.future.module.system.service.DictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.future.framework.common.constant.enums.OperateType.EXPORT;

@Api(tags = "管理后台 - 字典类型")
@RestController
@RequestMapping("/system/dict-type")
@Validated
public class DictTypeController {

    @Resource
    private DictTypeService dictTypeService;

    @PostMapping("/create")
    @ApiOperation("创建字典类型")
    @PreAuthorize("@ss.hasPermission('system:dict:create')")
    public R createDictType(@Valid @RequestBody DictTypeCreateQuery query) {
        Long dictTypeId = dictTypeService.createDictType(query);
        return R.ok(dictTypeId);
    }

    @PutMapping("/update")
    @ApiOperation("修改字典类型")
    @PreAuthorize("@ss.hasPermission('system:dict:update')")
    public R updateDictType(@Valid @RequestBody DictTypeUpdateQuery query) {
        dictTypeService.updateDictType(query);
        return R.ok(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除字典类型")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:dict:delete')")
    public R deleteDictType(Long id) {
        dictTypeService.deleteDictType(id);
        return R.ok(true);
    }

    @ApiOperation("获得字典类型的分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public R pageDictTypes(@Valid DictTypePageQuery query) {
        return R.ok(DictTypeConvert.INSTANCE.convertPage(dictTypeService.getDictTypePage(query)));
    }

    @ApiOperation("查询字典类型详细")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @GetMapping(value = "/get")
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public R getDictType(@RequestParam("id") Long id) {
        return R.ok(DictTypeConvert.INSTANCE.convert(dictTypeService.getDictType(id)));
    }

    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获得全部字典类型列表", notes = "包括开启 + 禁用的字典类型，主要用于前端的下拉选项")
    // 无需添加权限认证，因为前端全局都需要
    public R listSimpleDictTypes() {
        List<DictType> list = dictTypeService.getDictTypeList();
        return R.ok(DictTypeConvert.INSTANCE.convertList(list));
    }

    @ApiOperation("导出数据类型")
    @GetMapping("/export")
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    @OperateLog(type = EXPORT)
    public void export(HttpServletResponse response, @Valid DictTypeExportQuery query) throws IOException {
        List<DictType> list = dictTypeService.getDictTypeList(query);
        List<DictTypeExcelVO> data = DictTypeConvert.INSTANCE.convertToExcelList(list);
        // 输出
        ExcelUtils.write(response, "字典类型.xls", "类型列表", DictTypeExcelVO.class, data);
    }

}
