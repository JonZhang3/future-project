package com.future.system.dao;

import com.future.common.constant.enums.State;
import com.future.common.core.domain.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long> {
    
    int countByParentIdAndStateNot(Long parentId, State state);
    
    Department findByParentIdAndNameAndStateNot(Long parentId, String name, State state);

    @Modifying
    @Query("update Department SET state = ?2 where id = ?1")
    void updateState(Long id, State state);
    
}
