package com.future.module.system.dao;

import com.future.framework.common.domain.PageResult;
import com.future.framework.mybatis.mapper.BaseMapper;
import com.future.framework.mybatis.query.LambdaQueryWrapper;
import com.future.module.system.domain.entity.Role;
import com.future.module.system.domain.query.permission.RoleExportQuery;
import com.future.module.system.domain.query.permission.RolePageQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    default PageResult<Role> selectPage(RolePageQuery query) {
        return selectPage(query.getPageNo(), query.getPageSize(), new LambdaQueryWrapper<Role>()
            .likeIfPresent(Role::getName, query.getName())
            .likeIfPresent(Role::getCode, query.getCode())
            .eqIfPresent(Role::getStatus, query.getStatus())
            .betweenIfPresent(Role::getCreateTime, query.getCreateTime())
            .orderByDesc(Role::getId));
    }

    default List<Role> selectList(RoleExportQuery query) {
        return selectList(new LambdaQueryWrapper<Role>()
            .likeIfPresent(Role::getName, query.getName())
            .likeIfPresent(Role::getCode, query.getCode())
            .eqIfPresent(Role::getStatus, query.getStatus())
            .betweenIfPresent(Role::getCreateTime, query.getCreateTime()));
    }

    default Role selectByName(String name) {
        return selectOne(Role::getName, name);
    }

    default Role selectByCode(String code) {
        return selectOne(Role::getCode, code);
    }

    default List<Role> selectListByStatus(@Nullable Collection<Integer> statuses) {
        return selectList(Role::getStatus, statuses);
    }

    @Select("SELECT COUNT(*) FROM system_role WHERE update_time > #{maxUpdateTime}")
    Long selectCountByUpdateTimeGt(Date maxUpdateTime);

}
