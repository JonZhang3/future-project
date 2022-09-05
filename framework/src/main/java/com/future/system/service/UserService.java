package com.future.system.service;

import com.future.common.core.domain.entity.User;
import com.future.system.domain.query.UserQuery;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 用户 service
 * 
 * @author JonZhang
 */
public interface UserService {

    /**
     * 根据条件分页查询用户列表
     */
    Page<User> pageListUsers(UserQuery query);

    /**
     * 根据条件分页查询已分配用户角色列表
     */
    List<User> pageFindAllocatedUsers();
    
    void deleteUsersByIds(List<Long> ids);
    
}
