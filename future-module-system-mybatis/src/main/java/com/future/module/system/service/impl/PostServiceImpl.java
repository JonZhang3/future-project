package com.future.module.system.service.impl;

import com.future.framework.common.constant.enums.CommonStatus;
import com.future.framework.common.domain.PageResult;
import com.future.framework.common.exception.ServiceException;
import com.future.framework.common.utils.CollUtils;
import com.future.module.system.dao.PostMapper;
import com.future.module.system.domain.convert.PostConvert;
import com.future.module.system.domain.entity.Post;
import com.future.module.system.domain.query.dept.PostCreateQuery;
import com.future.module.system.domain.query.dept.PostExportQuery;
import com.future.module.system.domain.query.dept.PostPageQuery;
import com.future.module.system.domain.query.dept.PostUpdateQuery;
import com.future.module.system.service.PostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.future.module.system.constants.enums.SystemErrorCode.*;

/**
 * 岗位 Service 实现类
 *
 * @author JonZhang
 */
@Service
public class PostServiceImpl implements PostService {

    @Resource
    private PostMapper postMapper;

    @Override
    public Long createPost(PostCreateQuery query) {
        // 校验正确性
        this.checkCreateOrUpdate(null, query.getName(), query.getCode());
        // 插入岗位
        Post post = PostConvert.INSTANCE.convert(query);
        postMapper.insert(post);
        return post.getId();
    }

    @Override
    public void updatePost(PostUpdateQuery query) {
        // 校验正确性
        this.checkCreateOrUpdate(query.getId(), query.getName(), query.getCode());
        // 更新岗位
        Post updateObj = PostConvert.INSTANCE.convert(query);
        postMapper.updateById(updateObj);
    }

    @Override
    public void deletePost(Long id) {
        // 校验是否存在
        this.checkPostExists(id);
        // 删除部门
        postMapper.deleteById(id);
    }

    @Override
    public List<Post> getPosts(Collection<Long> ids, Collection<Integer> statuses) {
        return postMapper.selectList(ids, statuses);
    }

    @Override
    public PageResult<Post> getPostPage(PostPageQuery query) {
        return postMapper.selectPage(query);
    }

    @Override
    public List<Post> getPosts(PostExportQuery query) {
        return postMapper.selectList(query);
    }

    @Override
    public Post getPost(Long id) {
        return postMapper.selectById(id);
    }

    @Override
    public void validPosts(Collection<Long> ids) {
        if (CollUtils.isEmpty(ids)) {
            return;
        }
        // 获得岗位信息
        List<Post> posts = postMapper.selectBatchIds(ids);
        Map<Long, Post> postMap = posts.stream().collect(Collectors.toMap(Post::getId, i -> i));
        // 校验
        ids.forEach(id -> {
            Post post = postMap.get(id);
            if (post == null) {
                throw new ServiceException(POST_NOT_FOUND);
            }
            if (!CommonStatus.VALID.getValue().equals(post.getStatus())) {
                throw new ServiceException(POST_NOT_ENABLE);
            }
        });
    }

    private void checkCreateOrUpdate(Long id, String name, String code) {
        // 校验自己存在
        checkPostExists(id);
        // 校验岗位名的唯一性
        checkPostNameUnique(id, name);
        // 校验岗位编码的唯一性
        checkPostCodeUnique(id, code);
    }

    private void checkPostNameUnique(Long id, String name) {
        Post post = postMapper.selectByName(name);
        if (post == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的岗位
        if (id == null) {
            throw new ServiceException(POST_NAME_DUPLICATE);
        }
        if (!post.getId().equals(id)) {
            throw new ServiceException(POST_NAME_DUPLICATE);
        }
    }

    private void checkPostCodeUnique(Long id, String code) {
        Post post = postMapper.selectByCode(code);
        if (post == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的岗位
        if (id == null) {
            throw new ServiceException(POST_CODE_DUPLICATE);
        }
        if (!post.getId().equals(id)) {
            throw new ServiceException(POST_CODE_DUPLICATE);
        }
    }

    private void checkPostExists(Long id) {
        if (id == null) {
            return;
        }
        Post post = postMapper.selectById(id);
        if (post == null) {
            throw new ServiceException(POST_NOT_FOUND);
        }
    }

}
