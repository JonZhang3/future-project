package com.future.module.admin.controller;

import com.future.framework.common.annotations.OperateLog;
import com.future.framework.common.domain.R;
import com.future.framework.common.utils.ExcelUtils;
import com.future.module.system.domain.convert.DictDataConvert;
import com.future.module.system.domain.entity.DictData;
import com.future.module.system.domain.query.dict.DictDataCreateQuery;
import com.future.module.system.domain.query.dict.DictDataExportQuery;
import com.future.module.system.domain.query.dict.DictDataPageQuery;
import com.future.module.system.domain.query.dict.DictDataUpdateQuery;
import com.future.module.system.service.DictDataService;
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

@Api(tags = "管理后台 - 字典数据")
@RestController
@RequestMapping("/system/dict-data")
@Validated
public class DictDataController {

    @Resource
    private DictDataService dictDataService;

    @PostMapping("/create")
    @ApiOperation("新增字典数据")
    @PreAuthorize("@ss.hasPermission('system:dict:create')")
    public R createDictData(@Valid @RequestBody DictDataCreateQuery query) {
        Long dictDataId = dictDataService.createDictData(query);
        return R.ok(dictDataId);
    }

    @PutMapping("update")
    @ApiOperation("修改字典数据")
    @PreAuthorize("@ss.hasPermission('system:dict:update')")
    public R updateDictData(@Valid @RequestBody DictDataUpdateQuery query) {
        dictDataService.updateDictData(query);
        return R.ok(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除字典数据")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:dict:delete')")
    public R deleteDictData(Long id) {
        dictDataService.deleteDictData(id);
        return R.ok(true);
    }

    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获得全部字典数据列表", notes = "一般用于管理后台缓存字典数据在本地")
    // 无需添加权限认证，因为前端全局都需要
    public R getSimpleDictDatas() {
        List<DictData> list = dictDataService.getDictDatas();
        return R.ok(DictDataConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("/获得字典类型的分页列表")
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public R getDictTypePage(@Valid DictDataPageQuery query) {
        return R.ok(DictDataConvert.INSTANCE.convertPage(dictDataService.getDictDataPage(query)));
    }

    @GetMapping(value = "/get")
    @ApiOperation("/查询字典数据详细")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public R getDictData(@RequestParam("id") Long id) {
        return R.ok(DictDataConvert.INSTANCE.convert(dictDataService.getDictData(id)));
    }

    @GetMapping("/export")
    @ApiOperation("导出字典数据")
    @PreAuthorize("@ss.hasPermission('system:dict:export')")
    @OperateLog(type = EXPORT)
    public void export(HttpServletResponse response, @Valid DictDataExportQuery query) throws IOException {
        List<DictData> list = dictDataService.getDictDatas(query);
        List<DictDataExcelVO> data = DictDataConvert.INSTANCE.convertList02(list);
        // 输出
        ExcelUtils.write(response, "字典数据.xls", "数据列表", DictDataExcelVO.class, data);
    }

}
