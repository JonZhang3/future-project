package com.future.cache.service;

import javax.annotation.Resource;

import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {
    
    @Resource
    private CacheManager cacheManager;

    

}
