package com.future.module.admin.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.future.framework.common.domain.R;
import com.future.module.system.domain.vo.permission.PermissionAssignRoleDataScopeReqVO;
import com.future.module.system.domain.vo.permission.PermissionAssignRoleMenuReqVO;
import com.future.module.system.domain.vo.permission.PermissionAssignUserRoleReqVO;
import com.future.module.system.service.PermissionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

//@Api(tags = "管理后台 - 权限")
@RestController
@RequestMapping("/admin-api/system/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

//    @ApiOperation("获得角色拥有的菜单编号")
//    @ApiImplicitParam(name = "roleId", value = "角色编号", required = true, dataTypeClass = Long.class)
    @GetMapping("/list-role-resources")
    @SaCheckPermission("system:permission:assign-role-menu")
    public R<Set<Long>> listRoleMenus(Long roleId) {
        return R.ok(permissionService.getRoleMenuIds(roleId));
    }

    @PostMapping("/assign-role-menu")
//    @ApiOperation("赋予角色菜单")
    @SaCheckPermission("system:permission:assign-role-menu")
    public R<Boolean> assignRoleMenu(@Validated @RequestBody PermissionAssignRoleMenuReqVO reqVO) {
        // 执行菜单的分配
        permissionService.assignRoleMenu(reqVO.getRoleId(), reqVO.getMenuIds());
        return R.ok(true);
    }

    @PostMapping("/assign-role-data-scope")
//    @ApiOperation("赋予角色数据权限")
    @SaCheckPermission("system:permission:assign-role-data-scope")
    public R<Boolean> assignRoleDataScope(@Valid @RequestBody PermissionAssignRoleDataScopeReqVO reqVO) {
        permissionService.assignRoleDataScope(reqVO.getRoleId(), reqVO.getDataScope(), reqVO.getDataScopeDeptIds());
        return R.ok(true);
    }

//    @ApiOperation("获得管理员拥有的角色编号列表")
//    @ApiImplicitParam(name = "userId", value = "用户编号", required = true, dataTypeClass = Long.class)
    @GetMapping("/list-user-roles")
    @SaCheckPermission("system:permission:assign-user-role")
    public R<Set<Long>> listAdminRoles(@RequestParam("userId") Long userId) {
        return R.ok(permissionService.getUserRoleIdListByUserId(userId));
    }

//    @ApiOperation("赋予用户角色")
    @PostMapping("/assign-user-role")
    @SaCheckPermission("system:permission:assign-user-role")
    public R<Boolean> assignUserRole(@Validated @RequestBody PermissionAssignUserRoleReqVO reqVO) {
        permissionService.assignUserRole(reqVO.getUserId(), reqVO.getRoleIds());
        return R.ok(true);
    }

}
