package com.future.system.service.impl;

import com.future.system.dao.OperationLogRepo;
import com.future.system.domain.entity.OperationLog;
import com.future.system.service.OperationLogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OperationLogServiceImpl implements OperationLogService {
    
    @Resource(type = OperationLogRepo.class)
    private OperationLogRepo operationLogRepo;

    @Async
    @Override
    public void asyncAddOperationLog(OperationLog log) {
        this.addOperationLog(log);
    }

    @Override
    public void addOperationLog(OperationLog log) {
        operationLogRepo.saveAndFlush(log);
    }

    @Override
    public void pageListOperationLogs() {
        
    }

    @Override
    public void deleteOperationLogByIds(List<Long> ids) {
        operationLogRepo.deleteAllById(ids);
    }

    @Override
    public void cleanOperationLog() {
        operationLogRepo.clearAllData();
    }
}
