package com.future.module.system.service;

import com.future.framework.common.domain.PageResult;
import com.future.module.system.domain.entity.LoginLog;
import com.future.module.system.domain.query.logger.LoginLogExportQuery;
import com.future.module.system.domain.query.logger.LoginLogPageQuery;

import java.util.List;

public interface LoginLogService {

    /**
     * 获得登录日志分页
     *
     * @param query 分页条件
     * @return 登录日志分页
     */
    PageResult<LoginLog> getLoginLogPage(LoginLogPageQuery query);

    /**
     * 获得登录日志列表
     *
     * @param query 列表条件
     * @return 登录日志列表
     */
    List<LoginLog> getLoginLogList(LoginLogExportQuery query);

    /**
     * 创建登录日志
     *
     * @param log 日志信息
     */
    void createLoginLog(LoginLog log);

}
