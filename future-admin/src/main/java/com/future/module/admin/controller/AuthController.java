package com.future.module.admin.controller;

import com.future.framework.common.annotations.OperateLog;
import com.future.framework.common.constant.enums.CommonStatus;
import com.future.framework.common.constant.enums.LoginLogType;
import com.future.framework.common.constant.enums.MenuType;
import com.future.framework.common.domain.R;
import com.future.framework.common.utils.StringUtils;
import com.future.framework.security.config.SecurityProperties;
import com.future.framework.security.util.SecurityUtils;
import com.future.module.system.domain.convert.AuthConvert;
import com.future.module.system.domain.entity.AdminUser;
import com.future.module.system.domain.entity.Menu;
import com.future.module.system.domain.entity.Role;
import com.future.module.system.domain.query.auth.AuthLoginQuery;
import com.future.module.system.service.AdminAuthService;
import com.future.module.system.service.UserService;
import com.future.module.system.service.PermissionService;
import com.future.module.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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

@Api(tags = "管理后台 - 认证")
@RestController
@RequestMapping("/system/auth")
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

    @Resource
    private SecurityProperties securityProperties;

    @PostMapping("/login")
    @PermitAll
    @ApiOperation("使用账号密码登录")
    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    public R login(@RequestBody @Valid AuthLoginQuery query) {
        return R.ok(authService.login(query));
    }

    @PostMapping("/logout")
    @PermitAll
    @ApiOperation("登出系统")
    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    public R logout(HttpServletRequest request) {
        String token = SecurityUtils.obtainAuthorization(request, securityProperties.getTokenHeader());
        if (StringUtils.isNotBlank(token)) {
            authService.logout(token, LoginLogType.LOGOUT_SELF.getType());
        }
        return R.ok(true);
    }

    @PostMapping("/refresh-token")
    @PermitAll
    @ApiOperation("刷新令牌")
    @ApiImplicitParam(name = "refreshToken", value = "刷新令牌", required = true, dataTypeClass = String.class)
    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    public R refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return R.ok(authService.refreshToken(refreshToken));
    }

    @GetMapping("/get-permission-info")
    @ApiOperation("获取登录用户的权限信息")
    public R getPermissionInfo() {
        // 获得用户信息
        AdminUser user = userService.getUser(getLoginUserId());
        if (user == null) {
            return null;
        }
        // 获得角色列表
        Set<Long> roleIds = permissionService.getUserRoleIdsFromCache(getLoginUserId(), singleton(CommonStatus.VALID.getValue()));
        List<Role> roleList = roleService.getRolesFromCache(roleIds);
        // 获得菜单列表
        List<Menu> menuList = permissionService.getRoleMenuListFromCache(roleIds,
            Arrays.asList(MenuType.DIR.getType(), MenuType.MENU.getType(), MenuType.BUTTON.getType()),
            singleton(CommonStatus.VALID.getValue())); // 只要开启的
        // 拼接结果返回
        return R.ok(AuthConvert.INSTANCE.convert(user, roleList, menuList));
    }

    @GetMapping("/list-menus")
    @ApiOperation("获得登录用户的菜单列表")
    public R getMenus() {
        // 获得角色列表
        Set<Long> roleIds = permissionService.getUserRoleIdsFromCache(getLoginUserId(), singleton(CommonStatus.VALID.getValue()));
        // 获得用户拥有的菜单列表
        List<Menu> menuList = permissionService.getRoleMenuListFromCache(roleIds,
            Arrays.asList(MenuType.DIR.getType(), MenuType.MENU.getType()), // 只要目录和菜单类型
            singleton(CommonStatus.VALID.getValue())); // 只要开启的
        // 转换成 Tree 结构返回
        return R.ok(AuthConvert.INSTANCE.buildMenuTree(menuList));
    }

}
