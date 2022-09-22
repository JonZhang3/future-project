package com.future.module.system.dao;

import com.future.framework.mybatis.mapper.BaseMapper;
import com.future.framework.mybatis.query.LambdaQueryWrapper;
import com.future.framework.mybatis.query.QueryWrapper;
import com.future.module.system.domain.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    default List<RoleMenu> selectListByRoleId(Long roleId) {
        return selectList(new QueryWrapper<RoleMenu>().eq("role_id", roleId));
    }

    default List<RoleMenu> selectListByRoleIds(Collection<Long> roleIds) {
        return selectList(new LambdaQueryWrapper<RoleMenu>().in(RoleMenu::getRoleId, roleIds));
    }
    
    default void deleteListByRoleIdAndMenuIds(Long roleId, Collection<Long> menuIds) {
        delete(new QueryWrapper<RoleMenu>().eq("role_id", roleId)
            .in("menu_id", menuIds));
    }

    default void deleteListByMenuId(Long menuId) {
        delete(new QueryWrapper<RoleMenu>().eq("menu_id", menuId));
    }

    default void deleteListByRoleId(Long roleId) {
        delete(new QueryWrapper<RoleMenu>().eq("role_id", roleId));
    }

    @Select("SELECT COUNT(*) FROM system_role_menu WHERE update_time > #{maxUpdateTime}")
    Long selectCountByUpdateTimeGt(Date maxUpdateTime);

}
