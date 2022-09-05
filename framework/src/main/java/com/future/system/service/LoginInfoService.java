package com.future.system.service;

import com.future.system.domain.entity.LoginInfo;
import com.future.system.domain.query.LoginInfoQuery;

import java.util.List;

/**
 * 系统访问日志
 * 
 * @author JonZhang
 */
public interface LoginInfoService {
    
    void addLoginInfo(LoginInfo loginInfo);
    
    void deleteLoginInfoByIds(List<Long> ids);
    
    void clearAllLoginInfo();
    
    void pageListLoginInfos(LoginInfoQuery query);
    
}
