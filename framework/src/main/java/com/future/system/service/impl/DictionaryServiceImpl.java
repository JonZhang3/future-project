package com.future.system.service.impl;

import com.future.common.constant.Constants;
import com.future.common.constant.enums.State;
import com.future.common.core.domain.entity.Dictionary;
import com.future.common.core.domain.entity.DictionaryItem;
import com.future.common.core.domain.entity.QDictionary;
import com.future.common.core.service.BaseService;
import com.future.common.exception.BusinessException;
import com.future.system.dao.DictionaryItemRepo;
import com.future.system.dao.DictionaryRepo;
import com.future.system.domain.query.DictionaryQuery;
import com.future.system.service.DictionaryService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service
public class DictionaryServiceImpl implements DictionaryService, BaseService {

    @Resource(type = DictionaryRepo.class)
    private DictionaryRepo dictionaryRepo;

    @Resource(type = DictionaryItemRepo.class)
    private DictionaryItemRepo dictionaryItemRepo;

    @Resource(type = JPAQueryFactory.class)
    private JPAQueryFactory queryFactory;

    @PostConstruct
    public void init() {
        loadingDictionaryCache();
    }

    @Override
    public Page<Dictionary> pageListDictionaries(DictionaryQuery dict) {
        QDictionary qdictionary = QDictionary.dictionary;
        return null;
    }

    public List<Dictionary> listDictionaries(DictionaryQuery dictQuery) {
        QDictionary qdictionary = QDictionary.dictionary;

        return null;
    }

    /**
     * 查询所有字典
     */
    @Override
    public List<Dictionary> listAllDictionaries() {
        return dictionaryRepo.findAll();
    }

    /**
     * 根据指定字典查询字典数据
     */
    @Override
    public List<DictionaryItem> listDictionaryItemsByCode(String code) {
        return dictionaryItemRepo.findAllByDictAndStateOrderBySortNumAsc(code, State.VALID);
    }

    /**
     * 根据字典ID查询字典
     */
    @Override
    public Dictionary getDictionaryById(Long id) {
        return dictionaryRepo.findById(id).orElse(null);
    }

    @Override
    public Dictionary getDictionaryByCode(String code) {
        return dictionaryRepo.findByCode(code);
    }

    @Override
    public void deleteByIds(List<Long> ids) {

    }

    @Override
    public void loadingDictionaryCache() {
//        Cache cache = cacheManager.getCache(Constants.CACHE_NAME_DICTIONARY);
//        if(cache == null) {
//            
//        }
    }

    @Override
    public void clearDictionaryCache() {
//        Cache cache = cacheManager.getCache(Constants.CACHE_NAME_DICTIONARY);
//        if (cache != null) {
//            cache.clear();
//        }
    }

    @Override
    public void resetDictionaryCache() {
        clearDictionaryCache();
        loadingDictionaryCache();
    }

    @CachePut(value = Constants.CACHE_NAME_DICTIONARY, key = "#dict.code")
    @Override
    public Dictionary addDictionary(@NotNull Dictionary dict) {
        dict.setState(State.VALID);
        return dictionaryRepo.saveAndFlush(dict);
    }

    @CachePut(value = Constants.CACHE_NAME_DICTIONARY, key = "#result.code")
    @Transactional
    @Override
    public Dictionary updateDictionary(Dictionary dict) {
        Dictionary oldDict = dictionaryRepo.findById(dict.getId()).orElse(null);
        if (oldDict == null) {
            throw new BusinessException("字典不存在");
        }
        return dictionaryRepo.saveAndFlush(dict);
    }

    @Override
    public boolean checkDictionaryCodeUnique(Dictionary dict) {
        Dictionary exists = dictionaryRepo.findByCodeAndStateNot(dict.getCode(), State.DELETED);
        return exists == null || exists.getId().equals(dict.getId());
    }

}
