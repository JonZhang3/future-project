package com.future.module.admin.controller;

import com.future.framework.common.constant.enums.CommonStatus;
import com.future.framework.common.domain.R;
import com.future.module.system.domain.entity.Department;
import com.future.module.system.domain.query.dept.DeptCreateQuery;
import com.future.module.system.domain.query.dept.DeptListQuery;
import com.future.module.system.domain.query.dept.DeptUpdateQuery;
import com.future.module.system.service.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

@Api(tags = "管理后台 - 部门")
@RestController
@RequestMapping("/system/dept")
@Validated
public class DeptController {

    @Resource
    private DeptService deptService;

    @PostMapping("create")
    @ApiOperation("创建部门")
    @PreAuthorize("@ss.hasPermission('system:dept:create')")
    public R createDept(@Valid @RequestBody DeptCreateQuery query) {
        Long deptId = deptService.createDept(query);
        return R.ok(deptId);
    }

    @PutMapping("update")
    @ApiOperation("更新部门")
    @PreAuthorize("@ss.hasPermission('system:dept:update')")
    public R updateDept(@Valid @RequestBody DeptUpdateQuery query) {
        deptService.updateDept(query);
        return R.ok(true);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除部门")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:dept:delete')")
    public R deleteDept(@RequestParam("id") Long id) {
        deptService.deleteDept(id);
        return R.ok(true);
    }

    @GetMapping("/list")
    @ApiOperation("获取部门列表")
    @PreAuthorize("@ss.hasPermission('system:dept:query')")
    public R listDepts(DeptListQuery query) {
        List<Department> list = deptService.getSimpleDepts(query);
        list.sort(Comparator.comparing(Department::getSort));
        return R.ok(list);
    }

    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获取部门精简信息列表", notes = "只包含被开启的部门，主要用于前端的下拉选项")
    public R getSimpleDepts() {
        // 获得部门列表，只要开启状态的
        DeptListQuery query = new DeptListQuery();
        query.setStatus(CommonStatus.VALID.getValue());
        List<Department> list = deptService.getSimpleDepts(query);
        // 排序后，返回给前端
        list.sort(Comparator.comparing(Department::getSort));
        return R.ok(list);
    }

    @GetMapping("/get")
    @ApiOperation("获得部门信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:dept:query')")
    public R getDept(@RequestParam("id") Long id) {
        return R.ok(deptService.getDept(id));
    }

}
