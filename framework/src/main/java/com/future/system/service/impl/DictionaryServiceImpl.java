package com.future.system.service.impl;

import com.future.common.core.domain.entity.Dictionary;
import com.future.common.core.domain.entity.QDictionary;
import com.future.common.core.service.BaseService;
import com.future.system.dao.DictionaryItemRepo;
import com.future.system.dao.DictionaryRepo;
import com.future.system.domain.query.DictionaryQuery;
import com.future.system.service.DictionaryService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

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
        
    }
    
    public void pageListDictionaries(DictionaryQuery dict) {
        QDictionary qdictionary = QDictionary.dictionary;
        
    }

    /**
     * 查询所有字典
     */
    @Override
    public List<Dictionary> listAllDictionaries() {
        return dictionaryRepo.findAll();
    }
    
    public void listDictionaryByCode(String code) {
        
    }
    
}
