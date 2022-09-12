package com.future.framework.security.service;

public class SecurityServiceImpl implements SecurityService {
    
    
    
    @Override
    public boolean hasPermission(String permission) {
        return false;
    }

    @Override
    public boolean hasAnyPermissions(String... permissions) {
        return false;
    }

    @Override
    public boolean hasRole(String role) {
        return false;
    }

    @Override
    public boolean hasAnyRoles(String... roles) {
        return false;
    }

    @Override
    public boolean hasScope(String scope) {
        return false;
    }

    @Override
    public boolean hasAnyScopes(String... scope) {
        return false;
    }
}
