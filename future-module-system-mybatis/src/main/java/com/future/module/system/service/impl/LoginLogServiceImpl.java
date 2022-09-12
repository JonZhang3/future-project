package com.future.module.system.service.impl;

import com.future.framework.common.domain.PageResult;
import com.future.module.system.dao.LoginLogMapper;
import com.future.module.system.domain.entity.LoginLog;
import com.future.module.system.domain.query.logger.LoginLogExportQuery;
import com.future.module.system.domain.query.logger.LoginLogPageQuery;
import com.future.module.system.service.LoginLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 登录日志 Service 实现
 * 
 * @author JonZhang
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Resource
    private LoginLogMapper loginLogMapper;

    @Override
    public PageResult<LoginLog> getLoginLogPage(LoginLogPageQuery query) {
        return loginLogMapper.selectPage(query);
    }

    @Override
    public List<LoginLog> getLoginLogList(LoginLogExportQuery query) {
        return loginLogMapper.selectList(query);
    }

    @Override
    public void createLoginLog(LoginLog log) {
        loginLogMapper.insert(log);
    }
}
