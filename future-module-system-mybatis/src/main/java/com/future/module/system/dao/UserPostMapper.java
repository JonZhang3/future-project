package com.future.module.system.dao;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.future.framework.mybatis.mapper.BaseMapper;
import com.future.framework.mybatis.query.LambdaQueryWrapper;
import com.future.module.system.domain.entity.UserPost;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface UserPostMapper extends BaseMapper<UserPost> {

    default List<UserPost> selectListByUserId(Long userId) {
        return selectList(new LambdaQueryWrapper<UserPost>()
            .eq(UserPost::getUserId, userId));
    }

    default void deleteByUserIdAndPostId(Long userId, Collection<Long> postIds) {
        delete(new LambdaQueryWrapper<UserPost>()
            .eq(UserPost::getUserId, userId)
            .in(UserPost::getPostId, postIds));
    }

    default List<UserPost> selectListByPostIds(Collection<Long> postIds) {
        return selectList(new LambdaQueryWrapper<UserPost>()
            .in(UserPost::getPostId, postIds));
    }

    default void deleteByUserId(Long userId) {
        delete(Wrappers.lambdaUpdate(UserPost.class).eq(UserPost::getUserId, userId));
    }

}
