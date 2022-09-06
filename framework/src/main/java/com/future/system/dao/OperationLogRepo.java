package com.future.system.dao;

import com.future.system.domain.entity.OperationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationLogRepo extends JpaRepository<OperationLog, Long> {
    
    @Query(value = "truncate table sys_operation_log", nativeQuery = true)
    @Modifying
    void clearAllData();
    
}
