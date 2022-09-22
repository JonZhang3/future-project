package com.future.module.admin.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import com.future.framework.common.annotations.OperateLog;
import com.future.framework.common.constant.enums.CommonStatus;
import com.future.framework.common.constant.enums.LoginLogType;
import com.future.framework.common.constant.enums.MenuType;
import com.future.framework.common.domain.R;
import com.future.module.system.domain.convert.AuthConvert;
import com.future.module.system.domain.entity.Menu;
import com.future.module.system.domain.entity.Role;
import com.future.module.system.domain.entity.User;
import com.future.module.system.domain.query.auth.AuthLoginQuery;
import com.future.module.system.domain.vo.auth.AuthLoginVO;
import com.future.module.system.domain.vo.auth.AuthMenuVO;
import com.future.module.system.domain.vo.auth.AuthPermissionInfoVO;
import com.future.module.system.service.AdminAuthService;
import com.future.module.system.service.PermissionService;
import com.future.module.system.service.RoleService;
import com.future.module.system.service.UserService;
import com.future.security.sa.util.SecurityUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.future.framework.web.util.WebUtils.getLoginUserId;
import static java.util.Collections.singleton;

@RestController
@RequestMapping("/admin-api/system/auth")
@Validated
public class AuthController {

    @Resource
    private AdminAuthService authService;
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;

    @PostMapping("/login")
    @SaIgnore
    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    public R<AuthLoginVO> login(@RequestBody @Valid AuthLoginQuery query) {
        return R.ok(authService.login(query));
    }

    @PostMapping("/logout")
    @SaIgnore
    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    public R<Boolean> logout(HttpServletRequest request) {
        authService.logout(LoginLogType.LOGOUT_SELF.getType());
        return R.ok(true);
    }

    @PostMapping("/refresh-token")
    @PermitAll
    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    public R<AuthLoginVO> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return R.ok(authService.refreshToken(refreshToken));
    }

    @GetMapping("/get-permission-info")
//    @ApiOperation("获取登录用户的权限信息")
    @SaCheckLogin
    public R<AuthPermissionInfoVO> getPermissionInfo() {
        // 获得用户信息
        User user = userService.getUser(SecurityUtils.getUserId());
        if (user == null) {
            return null;
        }
        // 获得角色列表
        Set<Long> roleIds = permissionService.getUserRoleIds(SecurityUtils.getUserId(), singleton(CommonStatus.VALID.getValue()));
        List<Role> roleList = roleService.getRolesByIds(roleIds);
        // 获得菜单列表
        List<Menu> menuList = permissionService.getRoleMenuList(roleIds,
            Arrays.asList(MenuType.DIR.getType(), MenuType.MENU.getType(), MenuType.BUTTON.getType()),
            singleton(CommonStatus.VALID.getValue())); // 只要开启的
        // 拼接结果返回
        return R.ok(AuthConvert.INSTANCE.convert(user, roleList, menuList));
    }

    @GetMapping("/list-menus")
//    @ApiOperation("获得登录用户的菜单列表")
    public R<List<AuthMenuVO>> getMenus() {
        // 获得角色列表
        Set<Long> roleIds = permissionService.getUserRoleIds(SecurityUtils.getUserId(), singleton(CommonStatus.VALID.getValue()));
        // 获得用户拥有的菜单列表
        List<Menu> menuList = permissionService.getRoleMenuList(roleIds,
            Arrays.asList(MenuType.DIR.getType(), MenuType.MENU.getType()), // 只要目录和菜单类型
            singleton(CommonStatus.VALID.getValue())); // 只要开启的
        // 转换成 Tree 结构返回
        return R.ok(AuthConvert.INSTANCE.buildMenuTree(menuList));
    }

}
