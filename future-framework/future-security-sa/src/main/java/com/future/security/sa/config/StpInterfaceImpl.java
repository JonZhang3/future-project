package com.future.security.sa.config;

import cn.dev33.satoken.stp.StpInterface;
import com.future.security.sa.service.SecurityService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private SecurityService securityService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return securityService.getPermissionList(loginId, loginType);
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return securityService.getRoleList(loginId, loginType);
    }

}
