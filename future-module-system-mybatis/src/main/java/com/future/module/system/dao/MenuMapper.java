package com.future.module.system.dao;

import com.future.framework.mybatis.mapper.BaseMapper;
import com.future.framework.mybatis.query.LambdaQueryWrapper;
import com.future.module.system.domain.entity.Menu;
import com.future.module.system.domain.query.permission.MenuListQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    default Menu selectByParentIdAndName(Long parentId, String name) {
        return selectOne(new LambdaQueryWrapper<Menu>().eq(Menu::getParentId, parentId)
            .eq(Menu::getName, name));
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount(Menu::getParentId, parentId);
    }

    default List<Menu> selectList(MenuListQuery query) {
        return selectList(new LambdaQueryWrapper<Menu>().likeIfPresent(Menu::getName, query.getName())
            .eqIfPresent(Menu::getStatus, query.getStatus()));
    }

    @Select("SELECT COUNT(*) FROM system_menu WHERE update_time > #{maxUpdateTime}")
    Long selectCountByUpdateTimeGt(Date maxUpdateTime);

}
