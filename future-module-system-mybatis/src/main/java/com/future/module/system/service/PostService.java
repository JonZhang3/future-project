package com.future.module.system.service;

import com.future.framework.common.constant.enums.CommonStatus;
import com.future.framework.common.domain.PageResult;
import com.future.module.system.domain.entity.Post;
import com.future.module.system.domain.query.dept.PostCreateQuery;
import com.future.module.system.domain.query.dept.PostExportQuery;
import com.future.module.system.domain.query.dept.PostPageQuery;
import com.future.module.system.domain.query.dept.PostUpdateQuery;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 岗位 Service 接口
 *
 * @author JonZhang
 */
public interface PostService {

    /**
     * 创建岗位
     *
     * @param query 岗位信息
     * @return 岗位编号
     */
    Long createPost(PostCreateQuery query);

    /**
     * 更新岗位
     *
     * @param query 岗位信息
     */
    void updatePost(PostUpdateQuery query);

    /**
     * 删除岗位信息
     *
     * @param id 岗位编号
     */
    void deletePost(Long id);

    /**
     * 获得岗位列表
     *
     * @param ids 岗位编号数组。如果为空，不进行筛选
     * @return 部门列表
     */
    default List<Post> getPosts(@Nullable Collection<Long> ids) {
        Set<Integer> set = new HashSet<>();
        set.add(CommonStatus.VALID.getValue());
        set.add(CommonStatus.INVALID.getValue());
        return getPosts(ids, set);
    }

    /**
     * 获得符合条件的岗位列表
     *
     * @param ids      岗位编号数组。如果为空，不进行筛选
     * @param statuses 状态数组。如果为空，不进行筛选
     * @return 部门列表
     */
    List<Post> getPosts(@Nullable Collection<Long> ids, @Nullable Collection<Integer> statuses);

    /**
     * 获得岗位分页列表
     *
     * @param query 分页条件
     * @return 部门分页列表
     */
    PageResult<Post> getPostPage(PostPageQuery query);

    /**
     * 获得岗位列表
     *
     * @param query 查询条件
     * @return 部门列表
     */
    List<Post> getPosts(PostExportQuery query);

    /**
     * 获得岗位信息
     *
     * @param id 岗位编号
     * @return 岗位信息
     */
    Post getPost(Long id);

    /**
     * 校验岗位们是否有效。如下情况，视为无效：
     * 1. 岗位编号不存在
     * 2. 岗位被禁用
     *
     * @param ids 岗位编号数组
     */
    void validPosts(Collection<Long> ids);

}
