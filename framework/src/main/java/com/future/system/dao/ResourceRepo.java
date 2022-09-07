package com.future.system.dao;

import com.future.common.constant.enums.State;
import com.future.common.core.domain.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepo extends JpaRepository<Resource, Long> {
    
    Resource findByParentIdAndNameAndStateNot(Long parentId, String name, State state);
    
    @Modifying
    @Query("update Resource set state = ?2 where id = ?1")
    void updateState(Long id, State state);
    
}
