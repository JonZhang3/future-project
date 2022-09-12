package com.future.module.system.service.impl;

import com.future.framework.common.constant.enums.CommonStatus;
import com.future.framework.common.constant.enums.DataScope;
import com.future.framework.common.constant.enums.RoleCode;
import com.future.framework.common.constant.enums.RoleType;
import com.future.framework.common.domain.PageResult;
import com.future.framework.common.exception.ServiceException;
import com.future.framework.common.utils.CollUtils;
import com.future.module.system.dao.RoleMapper;
import com.future.module.system.domain.convert.RoleConvert;
import com.future.module.system.domain.entity.Role;
import com.future.module.system.domain.query.permission.RoleCreateQuery;
import com.future.module.system.domain.query.permission.RoleExportQuery;
import com.future.module.system.domain.query.permission.RolePageQuery;
import com.future.module.system.domain.query.permission.RoleUpdateQuery;
import com.future.module.system.service.PermissionService;
import com.future.module.system.service.RoleService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.future.module.system.constants.enums.SystemErrorCode.*;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    /**
     * 定时执行 {@link #schedulePeriodicRefresh()} 的周期
     * 因为已经通过 Redis Pub/Sub 机制，所以频率不需要高
     */
    private static final long SCHEDULER_PERIOD = 5 * 60 * 1000L;

    /**
     * 角色缓存
     * key：角色编号 {@link Role#getId()}
     * <p>
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    @Getter
    private volatile Map<Long, Role> roleCache;
    /**
     * 缓存角色的最大更新时间，用于后续的增量轮询，判断是否有更新
     */
    @Getter
    private volatile Date maxUpdateTime;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RoleMapper roleMapper;

    @Resource
    @Lazy // 注入自己，所以延迟加载
    private RoleService self;

    @Override
    public void initLocalCache() {
        // 获取角色列表，如果有更新
        List<Role> roleList = loadRoleIfUpdate(maxUpdateTime);
        if (CollectionUtils.isEmpty(roleList)) {
            return;
        }

        // 写入缓存
        roleCache = CollUtils.convertMap(roleList, Role::getId);
        maxUpdateTime = CollUtils.getMaxValue(roleList, Role::getUpdateTime);
        log.info("[initLocalCache][初始化 Role 数量为 {}]", roleList.size());
    }

    @Scheduled(fixedDelay = SCHEDULER_PERIOD, initialDelay = SCHEDULER_PERIOD)
    public void schedulePeriodicRefresh() {
        self.initLocalCache();
    }
    
    @Override
    public Long createRole(RoleCreateQuery query, Integer type) {
        // 校验角色
        checkDuplicateRole(query.getName(), query.getCode(), null);
        // 插入到数据库
        Role role = RoleConvert.INSTANCE.convert(query);
        role.setType(ObjectUtils.defaultIfNull(type, RoleType.CUSTOM.getType()));
        role.setStatus(CommonStatus.VALID.getValue());
        role.setDataScope(DataScope.ALL.getScope()); // 默认可查看所有数据。原因是，可能一些项目不需要项目权限
        roleMapper.insert(role);
        // 返回
        return role.getId();
    }

    @Override
    public void updateRole(RoleUpdateQuery query) {
        // 校验是否可以更新
        checkUpdateRole(query.getId());
        // 校验角色的唯一字段是否重复
        checkDuplicateRole(query.getName(), query.getCode(), query.getId());

        // 更新到数据库
        Role updateObject = RoleConvert.INSTANCE.convert(query);
        roleMapper.updateById(updateObject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRole(Long id) {
        // 校验是否可以更新
        this.checkUpdateRole(id);
        // 标记删除
        roleMapper.deleteById(id);
        // 删除相关数据
        permissionService.processRoleDeleted(id);
    }

    @Override
    public void updateRoleStatus(Long id, Integer status) {
        // 校验是否可以更新
        checkUpdateRole(id);
        // 更新状态
        Role updateObject = new Role();
        updateObject.setId(id);
        updateObject.setStatus(status);
        roleMapper.updateById(updateObject);
    }

    @Override
    public void updateRoleDataScope(Long id, Integer dataScope, Set<Long> dataScopeDeptIds) {
        // 校验是否可以更新
        checkUpdateRole(id);
        // 更新数据范围
        Role updateObject = new Role();
        updateObject.setId(id);
        updateObject.setDataScope(dataScope);
        updateObject.setDataScopeDeptIds(dataScopeDeptIds);
        roleMapper.updateById(updateObject);
    }

    @Override
    public Role getRoleFromCache(Long id) {
        return roleCache.get(id);
    }

    @Override
    public List<Role> getRoles(Collection<Integer> statuses) {
        if (CollectionUtils.isEmpty(statuses)) {
            return roleMapper.selectList();
        }
        return roleMapper.selectListByStatus(statuses);
    }

    @Override
    public List<Role> getRolesFromCache(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return roleCache.values().stream().filter(roleDO -> ids.contains(roleDO.getId()))
            .collect(Collectors.toList());
    }

    @Override
    public boolean hasAnySuperAdmin(Collection<Role> roleList) {
        if (CollectionUtils.isEmpty(roleList)) {
            return false;
        }
        return roleList.stream().anyMatch(role -> RoleCode.isSuperAdmin(role.getCode()));
    }

    @Override
    public Role getRole(Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public PageResult<Role> getRolePage(RolePageQuery query) {
        return roleMapper.selectPage(query);
    }

    @Override
    public List<Role> getRoleList(RoleExportQuery query) {
        return roleMapper.selectList(query);
    }

    @Override
    public void validRoles(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        // 获得角色信息
        List<Role> roles = roleMapper.selectBatchIds(ids);
        Map<Long, Role> roleMap = CollUtils.convertMap(roles, Role::getId);
        // 校验
        ids.forEach(id -> {
            Role role = roleMap.get(id);
            if (role == null) {
                throw new ServiceException(ROLE_NOT_EXISTS);
            }
            if (!CommonStatus.VALID.getValue().equals(role.getStatus())) {
                throw new ServiceException(ROLE_IS_DISABLE);
            }
        });
    }

    /**
     * 如果角色发生变化，从数据库中获取最新的全量角色。
     * 如果未发生变化，则返回空
     *
     * @param maxUpdateTime 当前角色的最大更新时间
     * @return 角色列表
     */
    private List<Role> loadRoleIfUpdate(Date maxUpdateTime) {
        // 第一步，判断是否要更新。
        if (maxUpdateTime == null) { // 如果更新时间为空，说明 DB 一定有新数据
            log.info("[loadRoleIfUpdate][首次加载全量角色]");
        } else { // 判断数据库中是否有更新的角色
            if (roleMapper.selectCountByUpdateTimeGt(maxUpdateTime) == 0) {
                return null;
            }
            log.info("[loadRoleIfUpdate][增量加载全量角色]");
        }
        // 第二步，如果有更新，则从数据库加载所有角色
        return roleMapper.selectList();
    }

    public void checkDuplicateRole(String name, String code, Long id) {
        // 0. 超级管理员，不允许创建
        if (RoleCode.isSuperAdmin(code)) {
            throw new ServiceException(ROLE_ADMIN_CODE_ERROR);
        }
        // 1. 该 name 名字被其它角色所使用
        Role role = roleMapper.selectByName(name);
        if (role != null && !role.getId().equals(id)) {
            throw new ServiceException(ROLE_NAME_DUPLICATE);
        }
        // 2. 是否存在相同编码的角色
        if (!StringUtils.hasText(code)) {
            return;
        }
        // 该 code 编码被其它角色所使用
        role = roleMapper.selectByCode(code);
        if (role != null && !role.getId().equals(id)) {
            throw new ServiceException(ROLE_CODE_DUPLICATE);
        }
    }

    public void checkUpdateRole(Long id) {
        Role roleDO = roleMapper.selectById(id);
        if (roleDO == null) {
            throw new ServiceException(ROLE_NOT_EXISTS);
        }
        // 内置角色，不允许删除
        if (RoleType.SYSTEM.getType().equals(roleDO.getType())) {
            throw new ServiceException(ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE);
        }
    }

}
