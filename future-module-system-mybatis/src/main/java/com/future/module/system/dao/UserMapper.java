package com.future.module.system.dao;

import com.future.framework.common.domain.PageResult;
import com.future.framework.mybatis.mapper.BaseMapper;
import com.future.framework.mybatis.query.LambdaQueryWrapper;
import com.future.module.system.domain.entity.User;
import com.future.module.system.domain.query.user.UserExportQuery;
import com.future.module.system.domain.query.user.UserPageQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    default User selectByUsername(String username) {
        return selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    default User selectByEmail(String email) {
        return selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
    }

    default User selectByMobile(String mobile) {
        return selectOne(new LambdaQueryWrapper<User>().eq(User::getMobile, mobile));
    }

    default PageResult<User> selectPage(UserPageQuery query, Collection<Long> deptIds) {
        return selectPage(query.getPageNo(), query.getPageSize(), new LambdaQueryWrapper<User>()
            .likeIfPresent(User::getUsername, query.getUsername())
            .likeIfPresent(User::getMobile, query.getMobile())
            .eqIfPresent(User::getStatus, query.getStatus())
            .betweenIfPresent(User::getCreateTime, query.getCreateTime())
            .inIfPresent(User::getDeptId, deptIds)
            .orderByDesc(User::getId));
    }

    default List<User> selectList(UserExportQuery query, Collection<Long> deptIds) {
        return selectList(new LambdaQueryWrapper<User>()
            .likeIfPresent(User::getUsername, query.getUsername())
            .likeIfPresent(User::getMobile, query.getMobile())
            .eqIfPresent(User::getStatus, query.getStatus())
            .betweenIfPresent(User::getCreateTime, query.getCreateTime())
            .inIfPresent(User::getDeptId, deptIds));
    }

    default List<User> selectListByNickname(String nickname) {
        return selectList(new LambdaQueryWrapper<User>().like(User::getNickname, nickname));
    }

    default List<User> selectListByUsername(String username) {
        return selectList(new LambdaQueryWrapper<User>().like(User::getUsername, username));
    }

    default List<User> selectListByStatus(Integer status) {
        return selectList(User::getStatus, status);
    }

    default List<User> selectListByDeptIds(Collection<Long> deptIds) {
        return selectList(User::getDeptId, deptIds);
    }

}
