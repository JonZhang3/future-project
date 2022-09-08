package com.future.system.dao;

import com.future.common.constant.enums.State;
import com.future.common.core.domain.entity.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryRepo extends JpaRepository<Dictionary, Long> {
    
    Dictionary findByCode(String code);

    Dictionary findByCodeAndStateNot(String code, State state);
    
}
