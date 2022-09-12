package com.future.module.system.service.impl;

import com.future.framework.common.constant.enums.MenuType;
import com.future.framework.common.exception.ServiceException;
import com.future.framework.common.utils.CollUtils;
import com.future.framework.common.utils.StringUtils;
import com.future.module.system.constants.enums.MenuId;
import com.future.module.system.dao.MenuMapper;
import com.future.module.system.domain.convert.MenuConvert;
import com.future.module.system.domain.entity.Menu;
import com.future.module.system.domain.query.permission.MenuCreateQuery;
import com.future.module.system.domain.query.permission.MenuListQuery;
import com.future.module.system.domain.query.permission.MenuUpdateQuery;
import com.future.module.system.service.MenuService;
import com.future.module.system.service.PermissionService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.future.module.system.constants.enums.SystemErrorCode.*;

@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

    /**
     * 定时执行 {@link #schedulePeriodicRefresh()} 的周期
     * 因为已经通过 Redis Pub/Sub 机制，所以频率不需要高
     */
    private static final long SCHEDULER_PERIOD = 5 * 60 * 1000L;

    /**
     * 菜单缓存
     * key：菜单编号
     * <p>
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    private volatile Map<Long, Menu> menuCache;
    /**
     * 权限与菜单缓存
     * key：权限 {@link Menu#getPermission()}
     * value：MenuDO 数组，因为一个权限可能对应多个 MenuDO 对象
     * <p>
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    private volatile Multimap<String, Menu> permissionMenuCache;
    /**
     * 缓存菜单的最大更新时间，用于后续的增量轮询，判断是否有更新
     */
    private volatile Date maxUpdateTime;

    @Resource
    private MenuMapper menuMapper;
    
    @Resource
    private PermissionService permissionService;

    @Override
    public void initLocalCache() {
        // 获取菜单列表，如果有更新
        List<Menu> menuList = this.loadMenuIfUpdate(maxUpdateTime);
        if (CollectionUtils.isEmpty(menuList)) {
            return;
        }

        // 构建缓存
        ImmutableMap.Builder<Long, Menu> menuCacheBuilder = ImmutableMap.builder();
        ImmutableMultimap.Builder<String, Menu> permMenuCacheBuilder = ImmutableMultimap.builder();
        menuList.forEach(menuDO -> {
            menuCacheBuilder.put(menuDO.getId(), menuDO);
            if (StringUtils.isNotEmpty(menuDO.getPermission())) { // 会存在 permission 为 null 的情况，导致 put 报 NPE 异常
                permMenuCacheBuilder.put(menuDO.getPermission(), menuDO);
            }
        });
        menuCache = menuCacheBuilder.build();
        permissionMenuCache = permMenuCacheBuilder.build();
        maxUpdateTime = CollUtils.getMaxValue(menuList, Menu::getUpdateTime);
        log.info("[initLocalCache][缓存菜单，数量为:{}]", menuList.size());
    }

    @Scheduled(fixedDelay = SCHEDULER_PERIOD, initialDelay = SCHEDULER_PERIOD)
    public void schedulePeriodicRefresh() {
        initLocalCache();
    }

    @Override
    public Long createMenu(MenuCreateQuery query) {
        // 校验父菜单存在
        checkParentResource(query.getParentId(), null);
        // 校验菜单（自己）
        checkResource(query.getParentId(), query.getName(), null);
        // 插入数据库
        Menu menu = MenuConvert.INSTANCE.convert(query);
        initMenuProperty(menu);
        menuMapper.insert(menu);
        // 返回
        return menu.getId();
    }

    @Override
    public void updateMenu(MenuUpdateQuery query) {
        // 校验更新的菜单是否存在
        if (menuMapper.selectById(query.getId()) == null) {
            throw new ServiceException(MENU_NOT_EXISTS);
        }
        // 校验父菜单存在
        checkParentResource(query.getParentId(), query.getId());
        // 校验菜单（自己）
        checkResource(query.getParentId(), query.getName(), query.getId());
        // 更新到数据库
        Menu updateObject = MenuConvert.INSTANCE.convert(query);
        initMenuProperty(updateObject);
        menuMapper.updateById(updateObject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteMenu(Long id) {
        // 校验是否还有子菜单
        if (menuMapper.selectCountByParentId(id) > 0) {
            throw new ServiceException(MENU_EXISTS_CHILDREN);
        }
        // 校验删除的菜单是否存在
        if (menuMapper.selectById(id) == null) {
            throw new ServiceException(MENU_NOT_EXISTS);
        }
        // 标记删除
        menuMapper.deleteById(id);
        // 删除授予给角色的权限
        permissionService.processMenuDeleted(id);
    }

    @Override
    public List<Menu> getMenus() {
        return menuMapper.selectList();
    }

    @Override
    public List<Menu> getMenus(MenuListQuery query) {
        return menuMapper.selectList(query);
    }

    @Override
    public List<Menu> getMenuListFromCache(Collection<Integer> menuTypes, Collection<Integer> menusStatuses) {
        // 任一一个参数为空，则返回空
        if (CollectionUtils.isEmpty(menuTypes) || CollectionUtils.isEmpty(menusStatuses)) {
            return Collections.emptyList();
        }
        // 创建新数组，避免缓存被修改
        return menuCache.values().stream().filter(menu -> menuTypes.contains(menu.getType())
                && menusStatuses.contains(menu.getStatus()))
            .collect(Collectors.toList());
    }

    @Override
    public List<Menu> getMenuListFromCache(Collection<Long> menuIds, Collection<Integer> menuTypes, Collection<Integer> menusStatuses) {
        // 任一一个参数为空，则返回空
        if (CollectionUtils.isEmpty(menuIds) || CollectionUtils.isEmpty(menuTypes)
            || CollectionUtils.isEmpty(menusStatuses)) {
            return Collections.emptyList();
        }
        return menuCache.values().stream().filter(menu -> menuIds.contains(menu.getId())
                && menuTypes.contains(menu.getType())
                && menusStatuses.contains(menu.getStatus()))
            .collect(Collectors.toList());
    }

    @Override
    public List<Menu> getMenuListByPermissionFromCache(String permission) {
        return new ArrayList<>(permissionMenuCache.get(permission));
    }

    @Override
    public Menu getMenu(Long id) {
        return menuMapper.selectById(id);
    }

    /**
     * 如果菜单发生变化，从数据库中获取最新的全量菜单。
     * 如果未发生变化，则返回空
     *
     * @param maxUpdateTime 当前菜单的最大更新时间
     * @return 菜单列表
     */
    private List<Menu> loadMenuIfUpdate(Date maxUpdateTime) {
        // 第一步，判断是否要更新。
        if (maxUpdateTime == null) { // 如果更新时间为空，说明 DB 一定有新数据
            log.info("[loadMenuIfUpdate][首次加载全量菜单]");
        } else { // 判断数据库中是否有更新的菜单
            if (menuMapper.selectCountByUpdateTimeGt(maxUpdateTime) == 0) {
                return null;
            }
            log.info("[loadMenuIfUpdate][增量加载全量菜单]");
        }
        // 第二步，如果有更新，则从数据库加载所有菜单
        return menuMapper.selectList();
    }

    public void checkParentResource(Long parentId, Long childId) {
        if (parentId == null || MenuId.ROOT.getId().equals(parentId)) {
            return;
        }
        // 不能设置自己为父菜单
        if (parentId.equals(childId)) {
            throw new ServiceException(MENU_PARENT_ERROR);
        }
        Menu menu = menuMapper.selectById(parentId);
        // 父菜单不存在
        if (menu == null) {
            throw new ServiceException(MENU_PARENT_NOT_EXISTS);
        }
        // 父菜单必须是目录或者菜单类型
        if (!MenuType.DIR.getType().equals(menu.getType())
            && !MenuType.MENU.getType().equals(menu.getType())) {
            throw new ServiceException(MENU_PARENT_NOT_DIR_OR_MENU);
        }
    }

    public void checkResource(Long parentId, String name, Long id) {
        Menu menu = menuMapper.selectByParentIdAndName(parentId, name);
        if (menu == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的菜单
        if (id == null) {
            throw new ServiceException(MENU_NAME_DUPLICATE);
        }
        if (!menu.getId().equals(id)) {
            throw new ServiceException(MENU_NAME_DUPLICATE);
        }
    }

    /**
     * 初始化菜单的通用属性。
     * <p>
     * 例如说，只有目录或者菜单类型的菜单，才设置 icon
     *
     * @param menu 菜单
     */
    private void initMenuProperty(Menu menu) {
        // 菜单为按钮类型时，无需 component、icon、path 属性，进行置空
        if (MenuType.BUTTON.getType().equals(menu.getType())) {
            menu.setComponent("");
            menu.setIcon("");
            menu.setPath("");
        }
    }

}
