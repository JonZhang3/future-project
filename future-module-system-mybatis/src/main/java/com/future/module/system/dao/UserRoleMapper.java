package com.future.module.system.dao;

import com.future.framework.mybatis.mapper.BaseMapper;
import com.future.framework.mybatis.query.QueryWrapper;
import com.future.module.system.domain.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    default List<UserRole> selectListByUserId(Long userId) {
        return selectList(new QueryWrapper<UserRole>().eq("user_id", userId));
    }

    default List<UserRole> selectListByRoleId(Long roleId) {
        return selectList(new QueryWrapper<UserRole>().eq("role_id", roleId));
    }

    default void deleteListByUserIdAndRoleIdIds(Long userId, Collection<Long> roleIds) {
        delete(new QueryWrapper<UserRole>()
            .eq("user_id", userId)
            .in("role_id", roleIds));
    }

    default void deleteListByUserId(Long userId) {
        delete(new QueryWrapper<UserRole>().eq("user_id", userId));
    }

    default void deleteListByRoleId(Long roleId) {
        delete(new QueryWrapper<UserRole>().eq("role_id", roleId));
    }


    default List<UserRole> selectListByRoleIds(Collection<Long> roleIds) {
        return selectList(UserRole::getRoleId, roleIds);
    }

    @Select("SELECT COUNT(*) FROM system_user_role WHERE update_time > #{maxUpdateTime}")
    Long selectCountByUpdateTimeGt(Date maxUpdateTime);

}
