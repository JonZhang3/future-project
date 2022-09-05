package com.future.system.dao;

import com.future.system.domain.entity.LoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginInfoRepo extends JpaRepository<LoginInfo, Long> {
    
    @Query(value = "truncate table sys_logininfo", nativeQuery = true)
    @Modifying
    void clearAllLoginInfo();
    
}
