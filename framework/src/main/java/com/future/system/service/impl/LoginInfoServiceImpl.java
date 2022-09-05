package com.future.system.service.impl;

import com.future.system.dao.LoginInfoRepo;
import com.future.system.domain.entity.LoginInfo;
import com.future.system.domain.query.LoginInfoQuery;
import com.future.system.service.LoginInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LoginInfoServiceImpl implements LoginInfoService {
    
    @Resource(type = LoginInfoRepo.class)
    private LoginInfoRepo loginInfoRepo;
    
    @Override
    public void addLoginInfo(LoginInfo loginInfo) {
        loginInfoRepo.saveAndFlush(loginInfo);
    }

    @Override
    public void deleteLoginInfoByIds(List<Long> ids) {
        loginInfoRepo.deleteAllByIdInBatch(ids);
    }

    @Override
    public void clearAllLoginInfo() {
        loginInfoRepo.clearAllLoginInfo();
    }

    @Override
    public void pageListLoginInfos(LoginInfoQuery query) {
        
    }

}
