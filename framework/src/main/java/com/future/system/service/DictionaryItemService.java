package com.future.system.service;

import com.future.common.core.domain.entity.DictionaryItem;

import java.util.List;

public interface DictionaryItemService {

    String getItemLabel(String code, String itemVaue);

    DictionaryItem getItemById(Long id);

    void deleteByIds(List<Long> ids);

    void addItem(DictionaryItem item);

    void updateItem(DictionaryItem item);
    
}
