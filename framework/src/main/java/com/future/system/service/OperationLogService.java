package com.future.system.service;

import com.future.system.domain.entity.OperationLog;

import java.util.List;

/**
 * @author JonZhang
 */
public interface OperationLogService {

    void asyncAddOperationLog(OperationLog log);
    
    /**
     * 新增操作日志
     */
    void addOperationLog(OperationLog log);

    void pageListOperationLogs();
    
    /**
     * 指定ID列表删除操作日志
     */
    void deleteOperationLogByIds(List<Long> ids);

    /**
     * 清空操作日志
     */
    void cleanOperationLog();
    
}
