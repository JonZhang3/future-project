package com.future.system.service.impl;

import com.future.system.dao.DepartmentRepo;
import com.future.system.domain.query.DepartmentQuery;
import com.future.system.service.DepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    
    
    
    @Resource(type = DepartmentRepo.class)
    private DepartmentRepo deptRepo;
    
    public void listDepartments(DepartmentQuery query) {
        
    }
    
}
