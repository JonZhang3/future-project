package com.future.system.service;

import com.future.common.core.domain.entity.Dictionary;
import com.future.common.core.domain.entity.DictionaryItem;
import com.future.system.domain.query.DictionaryQuery;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DictionaryService {

    Page<Dictionary> pageListDictionaries(DictionaryQuery dict);
    
    List<Dictionary> listAllDictionaries();

    List<DictionaryItem> listDictionaryItemsByCode(String code);

    Dictionary getDictionaryById(Long id);

    Dictionary getDictionaryByCode(String code);

    void deleteByIds(List<Long> ids);
    
    void loadingDictionaryCache();
    
    void clearDictionaryCache();

    void resetDictionaryCache();
    
    Dictionary addDictionary(@NotNull Dictionary dict);

    Dictionary updateDictionary(Dictionary dict);

    boolean checkDictionaryCodeUnique(Dictionary dict);
    
}
