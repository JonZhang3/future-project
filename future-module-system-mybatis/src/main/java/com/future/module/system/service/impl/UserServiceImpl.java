package com.future.module.system.service.impl;

import com.future.framework.common.domain.PageResult;
import com.future.module.system.dao.AdminUserMapper;
import com.future.module.system.dao.UserPostMapper;
import com.future.module.system.domain.entity.AdminUser;
import com.future.module.system.domain.query.user.*;
import com.future.module.system.domain.vo.user.UserImportVO;
import com.future.module.system.service.UserService;
import com.future.module.system.service.DeptService;
import com.future.module.system.service.PermissionService;
import com.future.module.system.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    
    @Resource
    private AdminUserMapper userMapper;
    @Resource
    private DeptService deptService;
    @Resource
    private PostService postService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private UserPostMapper userPostMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long createUser(UserCreateQuery query) {
        
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUser(UserUpdateQuery query) {

    }

    @Override
    public void updateUserLogin(Long id, String loginIp) {

    }

    @Override
    public void updateUserProfile(Long id, UserProfileUpdateQuery query) {

    }

    @Override
    public void updateUserPassword(Long id, UserProfileUpdatePasswordQuery query) {

    }

    @Override
    public String updateUserAvatar(Long id, InputStream avatarFile) throws Exception {
        return null;
    }

    @Override
    public void updateUserPassword(Long id, String password) {

    }

    @Override
    public void updateUserStatus(Long id, Integer status) {

    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public AdminUser getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public AdminUser getUserByMobile(String mobile) {
        return null;
    }

    @Override
    public PageResult<AdminUser> getUserPage(UserPageQuery query) {
        return null;
    }

    @Override
    public AdminUser getUser(Long id) {
        return null;
    }

    @Override
    public List<AdminUser> getUsersByDeptIds(Collection<Long> deptIds) {
        return null;
    }

    @Override
    public List<AdminUser> getUsersByPostIds(Collection<Long> postIds) {
        return null;
    }

    @Override
    public List<AdminUser> getUsers(Collection<Long> ids) {
        return null;
    }

    @Override
    public void validUsers(Set<Long> ids) {

    }

    @Override
    public List<AdminUser> getUsers(UserExportQuery query) {
        return null;
    }

    @Override
    public List<AdminUser> getUsersByNickname(String nickname) {
        return null;
    }

    @Override
    public List<AdminUser> getUsersByUsername(String username) {
        return null;
    }

    @Override
    public UserImportVO importUsers(List<UserImportExcelQuery> importUsers, boolean isUpdateSupport) {
        return null;
    }

    @Override
    public List<AdminUser> getUsersByStatus(Integer status) {
        return null;
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return false;
    }
}
