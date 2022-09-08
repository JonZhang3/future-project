package com.future.system.service;

import com.future.common.core.domain.entity.Dictionary;
import com.future.common.core.domain.entity.DictionaryItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface DictionaryService {
    
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
