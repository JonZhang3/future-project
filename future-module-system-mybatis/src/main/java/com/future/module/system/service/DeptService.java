package com.future.module.system.service;

import com.future.module.system.domain.entity.Department;
import com.future.module.system.domain.query.dept.DeptCreateQuery;
import com.future.module.system.domain.query.dept.DeptListQuery;
import com.future.module.system.domain.query.dept.DeptUpdateQuery;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 部门 Service 接口
 *
 * @author JonZhang
 */
public interface DeptService {

    /**
     * 初始化部门的本地缓存
     */
    void initLocalCache();

    /**
     * 创建部门
     *
     * @param query 部门信息
     * @return 部门编号
     */
    Long createDept(DeptCreateQuery query);

    /**
     * 更新部门
     *
     * @param query 部门信息
     */
    void updateDept(DeptUpdateQuery query);

    /**
     * 删除部门
     *
     * @param id 部门编号
     */
    void deleteDept(Long id);

    /**
     * 筛选部门列表
     *
     * @param query 筛选条件请求 VO
     * @return 部门列表
     */
    List<Department> getSimpleDepts(DeptListQuery query);

    /**
     * 获得所有子部门，从缓存中
     *
     * @param parentId  部门编号
     * @param recursive 是否递归获取所有
     * @return 子部门列表
     */
    List<Department> getDeptsByParentIdFromCache(Long parentId, boolean recursive);

    /**
     * 获得部门信息数组
     *
     * @param ids 部门编号数组
     * @return 部门信息数组
     */
    List<Department> getDepts(Collection<Long> ids);

    /**
     * 获得部门信息
     *
     * @param id 部门编号
     * @return 部门信息
     */
    Department getDept(Long id);

    /**
     * 校验部门们是否有效。如下情况，视为无效：
     * 1. 部门编号不存在
     * 2. 部门被禁用
     *
     * @param ids 角色编号数组
     */
    void validDepts(Collection<Long> ids);

    /**
     * 获得指定编号的部门列表
     *
     * @param ids 部门编号数组
     * @return 部门列表
     */
    List<Department> getSimpleDepts(Collection<Long> ids);

    /**
     * 获得指定编号的部门 Map
     *
     * @param ids 部门编号数组
     * @return 部门 Map
     */
    default Map<Long, Department> getDeptMap(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyMap();
        }
        List<Department> list = getSimpleDepts(ids);
        return list.stream().collect(Collectors.toMap(Department::getId, i -> i));
    }

}