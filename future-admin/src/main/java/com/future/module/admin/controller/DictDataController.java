package com.future.module.admin.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.future.framework.common.annotations.OperateLog;
import com.future.framework.common.domain.PageResult;
import com.future.framework.common.domain.R;
import com.future.framework.common.utils.ExcelUtils;
import com.future.module.system.domain.convert.DictDataConvert;
import com.future.module.system.domain.entity.DictData;
import com.future.module.system.domain.query.dict.DictDataCreateQuery;
import com.future.module.system.domain.query.dict.DictDataExportQuery;
import com.future.module.system.domain.query.dict.DictDataPageQuery;
import com.future.module.system.domain.query.dict.DictDataUpdateQuery;
import com.future.module.system.domain.vo.dict.DictDataExcelVO;
import com.future.module.system.domain.vo.dict.DictDataRespVO;
import com.future.module.system.domain.vo.dict.DictDataSimpleVO;
import com.future.module.system.domain.vo.dict.DictDataVO;
import com.future.module.system.service.DictDataService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.future.framework.common.constant.enums.OperateType.EXPORT;

//@Api(tags = "管理后台 - 字典数据")
@RestController
@RequestMapping("/admin-api/system/dict-data")
@Validated
public class DictDataController {

    @Resource
    private DictDataService dictDataService;

    @PostMapping("/create")
//    @ApiOperation("新增字典数据")
    @SaCheckPermission("system:dict:create")
    public R<Long> createDictData(@Valid @RequestBody DictDataCreateQuery query) {
        return R.ok(dictDataService.createDictData(query));
    }

    @PutMapping("update")
//    @ApiOperation("修改字典数据")
    @SaCheckPermission("system:dict:update")
    public R<Boolean> updateDictData(@Valid @RequestBody DictDataUpdateQuery query) {
        dictDataService.updateDictData(query);
        return R.ok(true);
    }

    @DeleteMapping("/delete")
//    @ApiOperation("删除字典数据")
//    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @SaCheckPermission("system:dict:delete")
    public R<Boolean> deleteDictData(Long id) {
        dictDataService.deleteDictData(id);
        return R.ok(true);
    }

    @GetMapping("/list-all-simple")
//    @ApiOperation(value = "获得全部字典数据列表", notes = "一般用于管理后台缓存字典数据在本地")
    // 无需添加权限认证，因为前端全局都需要
    public R<List<DictDataSimpleVO>> getSimpleDictDatas() {
        List<DictData> list = dictDataService.getDictDatas();
        return R.ok(DictDataConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
//    @ApiOperation("/获得字典类型的分页列表")
    @SaCheckPermission("system:dict:query")
    public R<PageResult<DictDataVO>> getDictTypePage(@Valid DictDataPageQuery query) {
        return R.ok(DictDataConvert.INSTANCE.convertPage(dictDataService.getDictDataPage(query)));
    }

    @GetMapping(value = "/get")
//    @ApiOperation("/查询字典数据详细")
//    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @SaCheckPermission("system:dict:query")
    public R<DictDataRespVO> getDictData(@RequestParam("id") Long id) {
        return R.ok(DictDataConvert.INSTANCE.convert(dictDataService.getDictData(id)));
    }

    @GetMapping("/export")
//    @ApiOperation("导出字典数据")
    @SaCheckPermission("system:dict:export")
    @OperateLog(type = EXPORT)
    public void export(HttpServletResponse response, @Valid DictDataExportQuery query) throws IOException {
        List<DictData> list = dictDataService.getDictDatas(query);
        List<DictDataExcelVO> data = DictDataConvert.INSTANCE.convertToExcelList(list);
        // 输出
        ExcelUtils.write(response, "字典数据.xls", "数据列表", DictDataExcelVO.class, data);
    }

}
