package com.future.system.service.impl;

import com.future.common.constant.enums.State;
import com.future.common.constant.enums.UserState;
import com.future.common.core.domain.TreeSelect;
import com.future.common.core.domain.entity.Department;
import com.future.common.core.domain.entity.QDepartment;
import com.future.common.exception.BusinessException;
import com.future.common.utils.StringUtils;
import com.future.system.dao.DepartmentRepo;
import com.future.system.dao.UserRepo;
import com.future.system.domain.query.DepartmentQuery;
import com.future.system.service.DepartmentService;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource(type = JPAQueryFactory.class)
    private JPAQueryFactory queryFactory;

    @Resource(type = DepartmentRepo.class)
    private DepartmentRepo deptRepo;
    
    @Resource(type = UserRepo.class)
    private UserRepo userRepo;

    @Override
    public List<Department> listDepartments(DepartmentQuery query) {
        QDepartment department = QDepartment.department;
        JPAQuery<Department> dbquery = queryFactory.selectFrom(department);
        if (query.getId() != null) {
            dbquery.where(department.id.eq(query.getId()));
        }
        if (query.getParentId() != null) {
            dbquery.where(department.parentId.eq(query.getParentId()));
        }
        if (StringUtils.isNotEmpty(query.getName())) {
            dbquery.where(department.name.like("%" + query.getName() + "%"));
        }
        if (query.getState() != null) {
            dbquery.where(department.state.eq(query.getState()));
        }
        return dbquery.orderBy(department.parentId.asc(), department.sortNum.asc())
            .fetch();
    }

    /**
     * 构建前端所需树解构
     */
    @Override
    public List<Department> buildDepartmentTree(List<Department> depts) {
        List<Department> returnList = new LinkedList<>();
        List<Long> tempList = new LinkedList<>();
        for (Department dept : depts) {
            tempList.add(dept.getId());
        }
        for (Department dept : depts) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId())) {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty()) {
            returnList = depts;
        }
        return returnList;
    }

    @Override
    public List<TreeSelect> buildDepartmentTreeSelect(List<Department> depts) {
        List<Department> deptTrees = buildDepartmentTree(depts);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public Department getDepartmentById(Long id) {
        return deptRepo.findById(id).orElse(null);
    }
    
    @Override
    public boolean hasChildrenById(Long id) {
        return deptRepo.countByParentIdAndStateNot(id, State.DELETED) > 0;
    }
    
    public boolean checkExistsUser(Long deptId) {
        return userRepo.countByDeptIdAndStateIsNot(deptId, UserState.DELETED) > 0;
    }

    /**
     * 检查部门名称是否唯一
     */
    @Override
    public boolean checkDepartmentNameUnique(DepartmentQuery query) {
        Department department = deptRepo.findByParentIdAndNameAndStateNot(query.getParentId(), query.getName(), State.DELETED);
        return department == null || Objects.equals(department.getId(), query.getId());
    }

    /**
     * 新增部门
     */
    @Override
    public void addDepartment(Department dept) {
        Department parentDept = deptRepo.findById(dept.getParentId()).orElse(null);
        if(parentDept == null) {
            throw new BusinessException("上级部门不存在");
        }
        if(!State.VALID.equals(parentDept.getState())) {
            throw new BusinessException("上级部门停用，不允许新增子部门");
        }
        dept.setAncestors(parentDept.getAncestors() + "," + dept.getParentId());
        dept.setState(State.VALID);
        deptRepo.saveAndFlush(dept);
    }
    
    public void updateDepartment(Department dept) {
        Department newParentDept = deptRepo.findById(dept.getParentId()).orElse(null);
        if(newParentDept != null) {
            
        }
    }
    
    /**
     * 删除部门信息
     */
    @Override
    public void deleteById(Long id) {
        deptRepo.updateState(id, State.DELETED);
    }

    private void recursionFn(List<Department> list, Department t) {
        // 得到子节点列表
        List<Department> childList = getChildList(list, t);
        t.setChildren(childList);
        for (Department tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<Department> getChildList(List<Department> list, Department t) {
        List<Department> tlist = new LinkedList<>();
        for (Department n : list) {
            if (n.getParentId() != null && Objects.equals(n.getParentId(), t.getId())) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<Department> list, Department t) {
        return getChildList(list, t).size() > 0;
    }

}
