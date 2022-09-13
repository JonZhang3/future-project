package com.future.module.system.security;

import com.future.framework.common.utils.CollUtils;
import com.future.framework.security.domain.LoginUser;
import com.future.framework.security.service.SecurityService;
import com.future.framework.security.util.SecurityUtils;
import com.future.module.system.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;

@Service("ss")
public class SecurityServiceImpl implements SecurityService {

    @Resource
    private PermissionService permissionService;

    @Override
    public boolean hasPermission(String permission) {
        return hasAnyPermissions(permission);
    }

    @Override
    public boolean hasAnyPermissions(String... permissions) {
        return permissionService.hasAnyPermissions(SecurityUtils.getLoginUserId(), permissions);
    }

    @Override
    public boolean hasRole(String role) {
        return hasAnyRoles(role);
    }

    @Override
    public boolean hasAnyRoles(String... roles) {
        return permissionService.hasAnyRoles(SecurityUtils.getLoginUserId(), roles);
    }

    @Override
    public boolean hasScope(String scope) {
        return hasAnyScopes(scope);
    }

    @Override
    public boolean hasAnyScopes(String... scope) {
        LoginUser user = SecurityUtils.getLoginUser();
        if (user == null) {
            return false;
        }
        return CollUtils.containsAny(user.getScopes(), Arrays.asList(scope));
    }
}
