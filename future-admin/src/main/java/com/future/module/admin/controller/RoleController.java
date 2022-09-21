package com.future.module.admin.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.future.framework.common.annotations.OperateLog;
import com.future.framework.common.constant.enums.CommonStatus;
import com.future.framework.common.domain.PageResult;
import com.future.framework.common.domain.R;
import com.future.framework.common.utils.ExcelUtils;
import com.future.module.system.domain.convert.RoleConvert;
import com.future.module.system.domain.entity.Role;
import com.future.module.system.domain.query.permission.RoleCreateQuery;
import com.future.module.system.domain.query.permission.RoleExportQuery;
import com.future.module.system.domain.query.permission.RolePageQuery;
import com.future.module.system.domain.query.permission.RoleUpdateQuery;
import com.future.module.system.domain.vo.permission.RoleExcelVO;
import com.future.module.system.domain.vo.permission.RoleRespVO;
import com.future.module.system.domain.vo.permission.RoleSimpleRespVO;
import com.future.module.system.domain.vo.permission.RoleUpdateStatusReqVO;
import com.future.module.system.service.RoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.future.framework.common.constant.enums.OperateType.EXPORT;

//@Api(tags = "管理后台 - 角色")
@RestController
@RequestMapping("/admin-api/system/role")
@Validated
public class RoleController {

    @Resource
    private RoleService roleService;

    @PostMapping("/create")
//    @ApiOperation("创建角色")
    @SaCheckPermission("system:role:create")
    public R<Long> createRole(@Valid @RequestBody RoleCreateQuery query) {
        return R.ok(roleService.createRole(query, null));
    }

    @PutMapping("/update")
//    @ApiOperation("修改角色")
    @SaCheckPermission("system:role:update")
    public R<Boolean> updateRole(@Valid @RequestBody RoleUpdateQuery query) {
        roleService.updateRole(query);
        return R.ok(true);
    }

    @PutMapping("/update-status")
//    @ApiOperation("修改角色状态")
    @SaCheckPermission("system:role:update")
    public R<Boolean> updateRoleStatus(@Valid @RequestBody RoleUpdateStatusReqVO reqVO) {
        roleService.updateRoleStatus(reqVO.getId(), reqVO.getStatus());
        return R.ok(true);
    }

    @DeleteMapping("/delete")
//    @ApiOperation("删除角色")
//    @ApiImplicitParam(name = "id", value = "角色编号", required = true, example = "1024", dataTypeClass = Long.class)
    @SaCheckPermission("system:role:delete")
    public R<Boolean> deleteRole(@RequestParam("id") Long id) {
        roleService.deleteRole(id);
        return R.ok(true);
    }

    @GetMapping("/get")
//    @ApiOperation("获得角色信息")
    @SaCheckPermission("system:role:query")
    public R<RoleRespVO> getRole(@RequestParam("id") Long id) {
        Role role = roleService.getRole(id);
        return R.ok(RoleConvert.INSTANCE.convert(role));
    }

    @GetMapping("/page")
//    @ApiOperation("获得角色分页")
    @SaCheckPermission("system:role:query")
    public R<PageResult<Role>> getRolePage(RolePageQuery query) {
        return R.ok(roleService.getRolePage(query));
    }

    @GetMapping("/list-all-simple")
//    @ApiOperation(value = "获取角色精简信息列表", notes = "只包含被开启的角色，主要用于前端的下拉选项")
    public R<List<RoleSimpleRespVO>> getSimpleRoles() {
        // 获得角色列表，只要开启状态的
        List<Role> list = roleService.getRoles(Collections.singleton(CommonStatus.VALID.getValue()));
        // 排序后，返回给前端
        list.sort(Comparator.comparing(Role::getSort));
        return R.ok(RoleConvert.INSTANCE.convertToSimpleRoleList(list));
    }

    @GetMapping("/export")
    @OperateLog(type = EXPORT)
    @SaCheckPermission("system:role:export")
    public void export(HttpServletResponse response, @Validated RoleExportQuery query) throws IOException {
        List<Role> list = roleService.getRoleList(query);
        List<RoleExcelVO> data = RoleConvert.INSTANCE.convertToRoleExcelList(list);
        // 输出
        ExcelUtils.write(response, "角色数据.xls", "角色列表", RoleExcelVO.class, data);
    }

}
