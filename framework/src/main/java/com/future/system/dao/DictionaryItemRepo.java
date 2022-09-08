package com.future.system.dao;

import com.future.common.constant.enums.State;
import com.future.common.core.domain.entity.DictionaryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictionaryItemRepo extends JpaRepository<DictionaryItem, Long> {
    
    List<DictionaryItem> findAllByDictAndStateOrderBySortNumAsc(String dictCode, State state);
    
}
