package com.future.security.sa.service;

import java.util.List;

public interface SecurityService {
    
    List<String> getPermissionList(Object userId, String userType);
    
    List<String> getRoleList(Object userId, String userType);
    
}
