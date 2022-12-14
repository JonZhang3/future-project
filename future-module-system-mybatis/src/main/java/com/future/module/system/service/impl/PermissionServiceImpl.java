package com.future.module.system.service.impl;

import com.future.framework.common.constant.enums.CommonStatus;
import com.future.framework.common.constant.enums.DataScope;
import com.future.framework.common.utils.ArrayUtils;
import com.future.framework.common.utils.CollUtils;
import com.future.framework.common.utils.JsonUtils;
import com.future.module.system.dao.RoleMenuMapper;
import com.future.module.system.dao.UserRoleMapper;
import com.future.module.system.domain.entity.*;
import com.future.module.system.domain.vo.DeptDataPermissionVO;
import com.future.module.system.service.*;
import com.google.common.base.Suppliers;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.future.framework.common.utils.CollUtils.convertSet;
import static java.util.Collections.singleton;

@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;
    @Resource
    private DeptService deptService;
    @Resource
    private UserService userService;

    @Override
    public List<Menu> getRoleMenuList(Collection<Long> roleIds, Collection<Integer> menuTypes,
                                      Collection<Integer> menusStatuses) {
        // 任一一个参数为空时，不返回任何菜单
        if (CollUtils.isAnyEmpty(roleIds, menuTypes, menusStatuses)) {
            return Collections.emptyList();
        }

        // 判断角色是否包含超级管理员。如果是超级管理员，获取到全部
        List<Role> roleList = roleService.getRolesByIds(roleIds);
        if (roleService.hasAnySuperAdmin(roleList)) {
            return menuService.getMenuListFromCache(menuTypes, menusStatuses);
        }
        List<RoleMenu> roleMenus = roleMenuMapper.selectListByRoleIds(roleIds);
        Set<Long> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toSet());
        // 获得角色拥有的菜单关联
        return menuService.getMenuListFromCache(menuIds, menuTypes, menusStatuses);
    }

    @Override
    public Set<Long> getUserRoleIds(Long userId, Collection<Integer> roleStatuses) {
        List<UserRole> userRoles = userRoleMapper.selectListByUserId(userId);
        // 创建用户的时候没有分配角色，会存在空指针异常
        if (CollUtils.isEmpty(userRoles)) {
            return Collections.emptySet();
        }
        Set<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
        // 过滤角色状态
        if (CollUtils.isNotEmpty(roleStatuses)) {
            roleIds.removeIf(roleId -> {
                Role role = roleService.getRoleFromCache(roleId);
                return role == null || !roleStatuses.contains(role.getStatus());
            });
        }
        return roleIds;
    }

    @Override
    public Set<Long> getRoleMenuIds(Long roleId) {
        // 如果是管理员的情况下，获取全部菜单编号
        if (roleService.hasAnySuperAdmin(Collections.singleton(roleId))) {
            return convertSet(menuService.getMenus(), Menu::getId);
        }
        // 如果是非管理员的情况下，获得拥有的菜单编号
        return convertSet(roleMenuMapper.selectListByRoleId(roleId), RoleMenu::getMenuId);
    }

    @Override
    public Set<Long> getUserRoleIdListByRoleIds(Collection<Long> roleIds) {
        return convertSet(userRoleMapper.selectListByRoleIds(roleIds),
            UserRole::getUserId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignRoleMenu(Long roleId, Set<Long> menuIds) {
        // 获得角色拥有菜单编号
        Set<Long> dbMenuIds = convertSet(roleMenuMapper.selectListByRoleId(roleId),
            RoleMenu::getMenuId);
        // 计算新增和删除的菜单编号
        Collection<Long> createMenuIds = CollUtils.subtract(menuIds, dbMenuIds);
        Collection<Long> deleteMenuIds = CollUtils.subtract(dbMenuIds, menuIds);
        // 执行新增和删除。对于已经授权的菜单，不用做任何处理
        if (!CollUtils.isEmpty(createMenuIds)) {
            roleMenuMapper.insertBatch(CollUtils.convertList(createMenuIds, menuId -> {
                RoleMenu entity = new RoleMenu();
                entity.setRoleId(roleId);
                entity.setMenuId(menuId);
                return entity;
            }));
        }
        if (!CollUtils.isEmpty(deleteMenuIds)) {
            roleMenuMapper.deleteListByRoleIdAndMenuIds(roleId, deleteMenuIds);
        }
    }

    @Override
    public Set<Long> getUserRoleIdListByUserId(Long userId) {
        return convertSet(userRoleMapper.selectListByUserId(userId),
            UserRole::getRoleId);
    }

    @Override
    public void assignUserRole(Long userId, Set<Long> roleIds) {
        // 获得角色拥有角色编号
        Set<Long> dbRoleIds = convertSet(userRoleMapper.selectListByUserId(userId),
            UserRole::getRoleId);
        // 计算新增和删除的角色编号
        Collection<Long> createRoleIds = CollUtils.subtract(roleIds, dbRoleIds);
        Collection<Long> deleteMenuIds = CollUtils.subtract(dbRoleIds, roleIds);
        // 执行新增和删除。对于已经授权的角色，不用做任何处理
        if (!CollUtils.isEmpty(createRoleIds)) {
            userRoleMapper.insertBatch(CollUtils.convertList(createRoleIds, roleId -> {
                UserRole entity = new UserRole();
                entity.setUserId(userId);
                entity.setRoleId(roleId);
                return entity;
            }));
        }
        if (!CollUtils.isEmpty(deleteMenuIds)) {
            userRoleMapper.deleteListByUserIdAndRoleIdIds(userId, deleteMenuIds);
        }
    }

    @Override
    public void assignRoleDataScope(Long roleId, Integer dataScope, Set<Long> dataScopeDeptIds) {
        roleService.updateRoleDataScope(roleId, dataScope, dataScopeDeptIds);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void processRoleDeleted(Long roleId) {
        // 标记删除 UserRole
        userRoleMapper.deleteListByRoleId(roleId);
        // 标记删除 RoleMenu
        roleMenuMapper.deleteListByRoleId(roleId);
    }

    @Override
    public void processMenuDeleted(Long menuId) {
        roleMenuMapper.deleteListByMenuId(menuId);
    }

    @Override
    public void processUserDeleted(Long userId) {
        userRoleMapper.deleteListByUserId(userId);
    }

    @Override
    public boolean hasAnyPermissions(Long userId, String... permissions) {
        // 如果为空，说明已经有权限
        if (ArrayUtils.isEmpty(permissions)) {
            return true;
        }

        // 获得当前登录的角色。如果为空，说明没有权限
        Set<Long> roleIds = getUserRoleIds(userId, singleton(CommonStatus.VALID.getValue()));
        if (CollUtils.isEmpty(roleIds)) {
            return false;
        }
        // 判断是否是超管。如果是，当然符合条件
        if (roleService.hasAnySuperAdmin(roleIds)) {
            return true;
        }
        List<RoleMenu> roleMenus = roleMenuMapper.selectListByRoleIds(roleIds);
        Map<Long, Set<Long>> menuRolesMap = CollUtils.convertMultiMap(roleMenus, RoleMenu::getMenuId, RoleMenu::getRoleId);
        // 遍历权限，判断是否有一个满足
        return Arrays.stream(permissions).anyMatch(permission -> {
            List<Menu> menuList = menuService.getMenuListByPermissionFromCache(permission);
            // 采用严格模式，如果权限找不到对应的 Menu 的话，认为
            if (CollUtils.isEmpty(menuList)) {
                return false;
            }
            // 获得是否拥有该权限，任一一个
            return menuList.stream().anyMatch(menu -> CollUtils.containsAny(roleIds, menuRolesMap.get(menu.getId())));
        });
    }

    @Override
    public boolean hasAnyRoles(Long userId, String... roles) {
        // 如果为空，说明已经有权限
        if (ArrayUtils.isEmpty(roles)) {
            return true;
        }

        // 获得当前登录的角色。如果为空，说明没有权限
        Set<Long> roleIds = getUserRoleIds(userId, singleton(CommonStatus.VALID.getValue()));
        if (CollUtils.isEmpty(roleIds)) {
            return false;
        }
        // 判断是否是超管。如果是，当然符合条件
        if (roleService.hasAnySuperAdmin(roleIds)) {
            return true;
        }
        Set<String> userRoles = convertSet(roleService.getRolesByIds(roleIds), Role::getCode);
        return CollUtils.containsAny(userRoles, Sets.newHashSet(roles));
    }

    @Override
    public DeptDataPermissionVO getDeptDataPermission(Long userId) {
        // 获得用户的角色
        Set<Long> roleIds = getUserRoleIds(userId, singleton(CommonStatus.VALID.getValue()));
        // 如果角色为空，则只能查看自己
        DeptDataPermissionVO result = new DeptDataPermissionVO();
        if (CollUtils.isEmpty(roleIds)) {
            result.setSelf(true);
            return result;
        }
        List<Role> roles = roleService.getRolesByIds(roleIds);

        // 获得用户的部门编号的缓存，通过 Guava 的 Suppliers 惰性求值，即有且仅有第一次发起 DB 的查询
        Supplier<Long> userDeptIdCache = Suppliers.memoize(() -> userService.getUser(userId).getDeptId());
        // 遍历每个角色，计算
        for (Role role : roles) {
            // 为空时，跳过
            if (role.getDataScope() == null) {
                continue;
            }
            // 情况一，ALL
            if (Objects.equals(role.getDataScope(), DataScope.ALL.getScope())) {
                result.setAll(true);
                continue;
            }
            // 情况二，DEPT_CUSTOM
            if (Objects.equals(role.getDataScope(), DataScope.DEPT_CUSTOM.getScope())) {
                CollUtils.addAll(result.getDeptIds(), role.getDataScopeDeptIds());
                // 自定义可见部门时，保证可以看到自己所在的部门。否则，一些场景下可能会有问题。
                // 例如说，登录时，基于 t_user 的 username 查询会可能被 dept_id 过滤掉
                CollUtils.addAll(result.getDeptIds(), userDeptIdCache.get());
                continue;
            }
            // 情况三，DEPT_ONLY
            if (Objects.equals(role.getDataScope(), DataScope.DEPT_ONLY.getScope())) {
                CollUtils.addIfNotNull(result.getDeptIds(), userDeptIdCache.get());
                continue;
            }
            // 情况四，DEPT_DEPT_AND_CHILD
            if (Objects.equals(role.getDataScope(), DataScope.DEPT_AND_CHILD.getScope())) {
                List<Department> depts = deptService.getDeptsByParentIdFromCache(userDeptIdCache.get(), true);
                CollUtils.addAll(result.getDeptIds(), CollUtils.convertList(depts, Department::getId));
                // 添加本身部门编号
                CollUtils.addAll(result.getDeptIds(), userDeptIdCache.get());
                continue;
            }
            // 情况五，SELF
            if (Objects.equals(role.getDataScope(), DataScope.SELF.getScope())) {
                result.setSelf(true);
                continue;
            }
            // 未知情况，error log 即可
            log.error("[getDeptDataPermission][LoginUser({}) role({}) 无法处理]", userId, JsonUtils.toJsonString(result));
        }
        return result;
    }

}
