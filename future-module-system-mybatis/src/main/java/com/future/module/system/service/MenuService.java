package com.future.module.system.service;

import com.future.module.system.domain.entity.Menu;
import com.future.module.system.domain.query.permission.MenuCreateQuery;
import com.future.module.system.domain.query.permission.MenuListQuery;
import com.future.module.system.domain.query.permission.MenuUpdateQuery;

import java.util.Collection;
import java.util.List;

public interface MenuService {

    /**
     * 初始化菜单的本地缓存
     */
    void initLocalCache();

    /**
     * 创建菜单
     *
     * @param query 菜单信息
     * @return 创建出来的菜单编号
     */
    Long createMenu(MenuCreateQuery query);

    /**
     * 更新菜单
     *
     * @param query 菜单信息
     */
    void updateMenu(MenuUpdateQuery query);

    /**
     * 删除菜单
     *
     * @param id 菜单编号
     */
    void deleteMenu(Long id);

    /**
     * 获得所有菜单列表
     *
     * @return 菜单列表
     */
    List<Menu> getMenus();

    /**
     * 筛选菜单列表
     *
     * @param query 筛选条件请求 VO
     * @return 菜单列表
     */
    List<Menu> getMenus(MenuListQuery query);

    /**
     * 获得所有菜单，从缓存中
     * <p>
     * 任一参数为空时，则返回为空
     *
     * @param menuTypes     菜单类型数组
     * @param menusStatuses 菜单状态数组
     * @return 菜单列表
     */
    List<Menu> getMenuListFromCache(Collection<Integer> menuTypes, Collection<Integer> menusStatuses);

    /**
     * 获得指定编号的菜单数组，从缓存中
     * <p>
     * 任一参数为空时，则返回为空
     *
     * @param menuIds       菜单编号数组
     * @param menuTypes     菜单类型数组
     * @param menusStatuses 菜单状态数组
     * @return 菜单数组
     */
    List<Menu> getMenuListFromCache(Collection<Long> menuIds, Collection<Integer> menuTypes,
                                    Collection<Integer> menusStatuses);

    /**
     * 获得权限对应的菜单数组
     *
     * @param permission 权限标识
     * @return 数组
     */
    List<Menu> getMenuListByPermissionFromCache(String permission);

    /**
     * 获得菜单
     *
     * @param id 菜单编号
     * @return 菜单
     */
    Menu getMenu(Long id);

}
