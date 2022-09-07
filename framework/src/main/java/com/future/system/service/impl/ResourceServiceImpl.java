package com.future.system.service.impl;

import com.future.system.dao.ResourceRepo;
import com.future.system.service.ResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Resource(type = ResourceRepo.class)
    private ResourceRepo resourceRepo;
    
    

}
