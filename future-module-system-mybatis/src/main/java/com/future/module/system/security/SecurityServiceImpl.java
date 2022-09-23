package com.future.module.system.security;

import com.future.module.system.service.PermissionService;
import com.future.security.sa.service.SecurityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Resource
    private PermissionService permissionService;

    @Override
    public List<String> getPermissionList(Object userId, String userType) {
        
        return null;
    }

    @Override
    public List<String> getRoleList(Object userId, String userType) {
        return null;
    }
}
