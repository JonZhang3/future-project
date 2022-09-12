package com.future.module.system.service;

import com.future.framework.common.domain.PageResult;
import com.future.module.system.domain.entity.OperateLog;
import com.future.module.system.domain.query.logger.OperateLogExportQuery;
import com.future.module.system.domain.query.logger.OperateLogPageQuery;

import java.util.List;

/**
 * 操作日志 Service 接口
 *
 * @author JonZhang
 */
public interface OperateLogService {

    /**
     * 记录操作日志
     *
     * @param log 操作日志
     */
    void createOperateLog(OperateLog log);

    /**
     * 获得操作日志分页列表
     *
     * @param query 分页条件
     * @return 操作日志分页列表
     */
    PageResult<OperateLog> getOperateLogPage(OperateLogPageQuery query);

    /**
     * 获得操作日志列表
     *
     * @param query 列表条件
     * @return 日志列表
     */
    List<OperateLog> getOperateLogs(OperateLogExportQuery query);

}
