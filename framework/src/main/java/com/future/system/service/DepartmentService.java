package com.future.system.service;

import com.future.common.core.domain.TreeSelect;
import com.future.common.core.domain.entity.Department;
import com.future.system.domain.query.DepartmentQuery;

import java.util.List;

public interface DepartmentService {

    List<Department> listDepartments(DepartmentQuery query);

    List<Department> buildDepartmentTree(List<Department> depts);

    List<TreeSelect> buildDepartmentTreeSelect(List<Department> depts);

    Department getDepartmentById(Long id);

    boolean hasChildrenById(Long id);

    boolean checkExistsUser(Long deptId);

    boolean checkDepartmentNameUnique(DepartmentQuery query);

    void updateDepartment(Department dept);
    
    void addDepartment(Department dept);
    
    void deleteById(Long id);
    
}
