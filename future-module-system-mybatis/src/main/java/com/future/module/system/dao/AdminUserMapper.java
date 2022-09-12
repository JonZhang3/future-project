package com.future.module.system.dao;

import com.future.framework.common.domain.PageResult;
import com.future.framework.mybatis.mapper.BaseMapper;
import com.future.framework.mybatis.query.LambdaQueryWrapper;
import com.future.module.system.domain.entity.AdminUser;
import com.future.module.system.domain.query.user.UserExportQuery;
import com.future.module.system.domain.query.user.UserPageQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface AdminUserMapper extends BaseMapper<AdminUser> {

    default AdminUser selectByUsername(String username) {
        return selectOne(new LambdaQueryWrapper<AdminUser>().eq(AdminUser::getUsername, username));
    }

    default AdminUser selectByEmail(String email) {
        return selectOne(new LambdaQueryWrapper<AdminUser>().eq(AdminUser::getEmail, email));
    }

    default AdminUser selectByMobile(String mobile) {
        return selectOne(new LambdaQueryWrapper<AdminUser>().eq(AdminUser::getMobile, mobile));
    }

    default PageResult<AdminUser> selectPage(UserPageQuery query, Collection<Long> deptIds) {
        return selectPage(query.getPageNo(), query.getPageSize(), new LambdaQueryWrapper<AdminUser>()
            .likeIfPresent(AdminUser::getUsername, query.getUsername())
            .likeIfPresent(AdminUser::getMobile, query.getMobile())
            .eqIfPresent(AdminUser::getStatus, query.getStatus())
            .betweenIfPresent(AdminUser::getCreateTime, query.getCreateTime())
            .inIfPresent(AdminUser::getDeptId, deptIds)
            .orderByDesc(AdminUser::getId));
    }

    default List<AdminUser> selectList(UserExportQuery query, Collection<Long> deptIds) {
        return selectList(new LambdaQueryWrapper<AdminUser>()
            .likeIfPresent(AdminUser::getUsername, query.getUsername())
            .likeIfPresent(AdminUser::getMobile, query.getMobile())
            .eqIfPresent(AdminUser::getStatus, query.getStatus())
            .betweenIfPresent(AdminUser::getCreateTime, query.getCreateTime())
            .inIfPresent(AdminUser::getDeptId, deptIds));
    }

    default List<AdminUser> selectListByNickname(String nickname) {
        return selectList(new LambdaQueryWrapper<AdminUser>().like(AdminUser::getNickname, nickname));
    }

    default List<AdminUser> selectListByUsername(String username) {
        return selectList(new LambdaQueryWrapper<AdminUser>().like(AdminUser::getUsername, username));
    }

    default List<AdminUser> selectListByStatus(Integer status) {
        return selectList(AdminUser::getStatus, status);
    }

    default List<AdminUser> selectListByDeptIds(Collection<Long> deptIds) {
        return selectList(AdminUser::getDeptId, deptIds);
    }

}
