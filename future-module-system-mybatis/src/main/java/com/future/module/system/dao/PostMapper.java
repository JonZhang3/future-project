package com.future.module.system.dao;

import com.future.framework.common.domain.PageResult;
import com.future.framework.mybatis.mapper.BaseMapper;
import com.future.framework.mybatis.query.QueryWrapper;
import com.future.module.system.domain.entity.Post;
import com.future.module.system.domain.query.dept.PostExportQuery;
import com.future.module.system.domain.query.dept.PostPageQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface PostMapper extends BaseMapper<Post> {

    default List<Post> selectList(Collection<Long> ids, Collection<Integer> statuses) {
        return selectList(new QueryWrapper<Post>().inIfPresent("id", ids)
            .inIfPresent("status", statuses));
    }

    default PageResult<Post> selectPage(PostPageQuery query) {
        return selectPage(query.getPageNo(), query.getPageSize(), new QueryWrapper<Post>()
            .likeIfPresent("code", query.getCode())
            .likeIfPresent("name", query.getName())
            .eqIfPresent("status", query.getStatus())
            .orderByDesc("id"));
    }

    default List<Post> selectList(PostExportQuery query) {
        return selectList(new QueryWrapper<Post>()
            .likeIfPresent("code", query.getCode())
            .likeIfPresent("name", query.getName())
            .eqIfPresent("status", query.getStatus()));
    }

    default Post selectByName(String name) {
        return selectOne(new QueryWrapper<Post>().eq("name", name));
    }

    default Post selectByCode(String code) {
        return selectOne(new QueryWrapper<Post>().eq("code", code));
    }

}
