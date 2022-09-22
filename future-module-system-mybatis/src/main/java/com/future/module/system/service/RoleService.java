package com.future.module.system.service;

import com.future.framework.common.domain.PageResult;
import com.future.module.system.domain.entity.Role;
import com.future.module.system.domain.query.permission.RoleCreateQuery;
import com.future.module.system.domain.query.permission.RoleExportQuery;
import com.future.module.system.domain.query.permission.RolePageQuery;
import com.future.module.system.domain.query.permission.RoleUpdateQuery;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface RoleService {

    /**
     * 初始化角色的本地缓存
     */
    void initLocalCache();

    /**
     * 创建角色
     *
     * @param query 创建角色信息
     * @param type  角色类型
     * @return 角色编号
     */
    Long createRole(@Valid RoleCreateQuery query, Integer type);

    /**
     * 更新角色
     *
     * @param query 更新角色信息
     */
    void updateRole(@Valid RoleUpdateQuery query);

    /**
     * 删除角色
     *
     * @param id 角色编号
     */
    void deleteRole(Long id);

    /**
     * 更新角色状态
     *
     * @param id     角色编号
     * @param status 状态
     */
    void updateRoleStatus(Long id, Integer status);

    /**
     * 设置角色的数据权限
     *
     * @param id               角色编号
     * @param dataScope        数据范围
     * @param dataScopeDeptIds 部门编号数组
     */
    void updateRoleDataScope(Long id, Integer dataScope, Set<Long> dataScopeDeptIds);

    /**
     * 获得角色，从缓存中
     *
     * @param id 角色编号
     * @return 角色
     */
    Role getRoleFromCache(Long id);

    /**
     * 获得角色列表
     *
     * @param statuses 筛选的状态。允许空，空时不筛选
     * @return 角色列表
     */
    List<Role> getRoles(@Nullable Collection<Integer> statuses);

    /**
     * 获得角色数组，从缓存中
     *
     * @param ids 角色编号数组
     * @return 角色数组
     */
    List<Role> getRolesByIds(Collection<Long> ids);

    /**
     * 判断角色数组中，是否有超级管理员
     *
     * @param roleList 角色数组
     * @return 是否有管理员
     */
    boolean hasAnySuperAdmin(Collection<Role> roleList);

    /**
     * 判断角色编号数组中，是否有管理员
     *
     * @param ids 角色编号数组
     * @return 是否有管理员
     */
    default boolean hasAnySuperAdmin(Set<Long> ids) {
        return hasAnySuperAdmin(getRolesByIds(ids));
    }

    /**
     * 获得角色
     *
     * @param id 角色编号
     * @return 角色
     */
    Role getRole(Long id);

    /**
     * 获得角色分页
     *
     * @param query 角色分页查询
     * @return 角色分页结果
     */
    PageResult<Role> getRolePage(RolePageQuery query);

    /**
     * 获得角色列表
     *
     * @param query 列表查询
     * @return 角色列表
     */
    List<Role> getRoleList(RoleExportQuery query);

    /**
     * 校验角色们是否有效。如下情况，视为无效：
     * 1. 角色编号不存在
     * 2. 角色被禁用
     *
     * @param ids 角色编号数组
     */
    void validRoles(Collection<Long> ids);

}
