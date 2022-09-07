package com.future.system.dao;

import com.future.common.constant.enums.State;
import com.future.common.core.domain.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long> {
    
    int countByParentIdAndStateIsNot(Long parentId, State state);
    
}
