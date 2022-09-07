package com.future.system.dao;

import com.future.common.core.domain.entity.DictionaryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryItemRepo extends JpaRepository<DictionaryItem, Long> {
}
