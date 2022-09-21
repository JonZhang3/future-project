package com.future.module.system.service.impl;

import com.future.framework.common.constant.enums.CommonStatus;
import com.future.framework.common.domain.PageResult;
import com.future.framework.common.exception.ServiceException;
import com.future.framework.common.utils.CollUtils;
import com.future.framework.common.utils.StringUtils;
import com.future.module.system.dao.UserMapper;
import com.future.module.system.dao.UserPostMapper;
import com.future.module.system.domain.convert.UserConvert;
import com.future.module.system.domain.entity.Department;
import com.future.module.system.domain.entity.User;
import com.future.module.system.domain.entity.UserPost;
import com.future.module.system.domain.query.user.*;
import com.future.module.system.domain.vo.user.UserImportVO;
import com.future.module.system.service.DeptService;
import com.future.module.system.service.PermissionService;
import com.future.module.system.service.PostService;
import com.future.module.system.service.UserService;
import com.future.security.sa.util.SecurityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;

import static com.future.framework.common.utils.CollUtils.convertList;
import static com.future.module.system.constants.enums.SystemErrorCode.*;

@Service
public class UserServiceImpl implements UserService {

    private final String userInitPassword = "12345";

    @Resource
    private UserMapper userMapper;
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
        // 校验正确性
        checkCreateOrUpdate(null, query.getUsername(), query.getMobile(), query.getEmail(),
            query.getDeptId(), query.getPostIds());
        // 插入用户
        User user = UserConvert.INSTANCE.convert(query);
        user.setStatus(CommonStatus.VALID.getValue()); // 默认开启
        user.setPassword(encodePassword(query.getPassword())); // 加密密码
        userMapper.insert(user);
        // 插入关联岗位
        if (CollUtils.isNotEmpty(user.getPostIds())) {
            userPostMapper.insertBatch(convertList(user.getPostIds(),
                postId -> new UserPost().setUserId(user.getId()).setPostId(postId)));
        }
        return user.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUser(UserUpdateQuery query) {
        // 校验正确性
        checkCreateOrUpdate(query.getId(), query.getUsername(), query.getMobile(), query.getEmail(),
            query.getDeptId(), query.getPostIds());
        // 更新用户
        User updateObj = UserConvert.INSTANCE.convert(query);
        userMapper.updateById(updateObj);
        // 更新岗位
        updateUserPost(query, updateObj);
    }

    @Override
    public void updateUserLogin(Long id, String loginIp) {
        userMapper.updateById(new User().setId(id).setLoginIp(loginIp).setLoginDate(new Date()));
    }

    @Override
    public void updateUserProfile(Long id, UserProfileUpdateQuery query) {
        // 校验正确性
        checkUserExists(id);
        checkEmailUnique(id, query.getEmail());
        checkMobileUnique(id, query.getMobile());
        // 执行更新
        userMapper.updateById(UserConvert.INSTANCE.convert(query).setId(id));
    }

    @Override
    public void updateUserPassword(Long id, UserProfileUpdatePasswordQuery query) {
        // 校验旧密码密码
        checkOldPassword(id, query.getOldPassword());
        // 执行更新
        User updateObj = new User().setId(id);
        updateObj.setPassword(encodePassword(query.getNewPassword())); // 加密密码
        userMapper.updateById(updateObj);
    }

    @Override
    public String updateUserAvatar(Long id, InputStream avatarFile) throws Exception {
        checkUserExists(id);
        // 存储文件
        String avatar = "";//fileApi.createFile(IoUtil.readBytes(avatarFile));
        // 更新路径
        User sysUserDO = new User();
        sysUserDO.setId(id);
        sysUserDO.setAvatar(avatar);
        userMapper.updateById(sysUserDO);
        return avatar;
    }

    @Override
    public void updateUserPassword(Long id, String password) {
        // 校验用户存在
        checkUserExists(id);
        // 更新密码
        User updateObj = new User();
        updateObj.setId(id);
        updateObj.setPassword(encodePassword(password)); // 加密密码
        userMapper.updateById(updateObj);
    }

    @Override
    public void updateUserStatus(Long id, Integer status) {
        // 校验用户存在
        checkUserExists(id);
        // 更新状态
        User updateObj = new User();
        updateObj.setId(id);
        updateObj.setStatus(status);
        userMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        // 校验用户存在
        checkUserExists(id);
        // 删除用户
        userMapper.deleteById(id);
        // 删除用户关联数据
        permissionService.processUserDeleted(id);
        // 删除用户岗位
        userPostMapper.deleteByUserId(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public User getUserByMobile(String mobile) {
        return userMapper.selectByMobile(mobile);
    }

    @Override
    public PageResult<User> getUserPage(UserPageQuery query) {
        return userMapper.selectPage(query, getDeptCondition(query.getDeptId()));
    }

    @Override
    public User getUser(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> getUsersByDeptIds(Collection<Long> deptIds) {
        if (CollUtils.isEmpty(deptIds)) {
            return Collections.emptyList();
        }
        return userMapper.selectListByDeptIds(deptIds);
    }

    @Override
    public List<User> getUsersByPostIds(Collection<Long> postIds) {
        if (CollUtils.isEmpty(postIds)) {
            return Collections.emptyList();
        }
        Set<Long> userIds = CollUtils.convertSet(userPostMapper.selectListByPostIds(postIds), UserPost::getUserId);
        if (CollUtils.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        return userMapper.selectBatchIds(userIds);
    }

    @Override
    public List<User> getUsers(Collection<Long> ids) {
        if (CollUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return userMapper.selectBatchIds(ids);
    }

    @Override
    public void validUsers(Set<Long> ids) {
        if (CollUtils.isEmpty(ids)) {
            return;
        }
        // 获得岗位信息
        List<User> users = userMapper.selectBatchIds(ids);
        Map<Long, User> userMap = CollUtils.convertMap(users, User::getId);
        // 校验
        ids.forEach(id -> {
            User user = userMap.get(id);
            if (user == null) {
                throw new ServiceException(USER_NOT_EXISTS);
            }
            if (!CommonStatus.VALID.getValue().equals(user.getStatus())) {
                throw new ServiceException(USER_IS_DISABLE);
            }
        });
    }

    @Override
    public List<User> getUsers(UserExportQuery query) {
        return userMapper.selectList(query, getDeptCondition(query.getDeptId()));
    }

    @Override
    public List<User> getUsersByNickname(String nickname) {
        return userMapper.selectListByNickname(nickname);
    }

    @Override
    public List<User> getUsersByUsername(String username) {
        return userMapper.selectListByUsername(username);
    }

    @Transactional(rollbackFor = Exception.class) // 添加事务，异常则回滚所有导入
    @Override
    public UserImportVO importUsers(List<UserImportExcelQuery> importUsers, boolean isUpdateSupport) {
        if (CollUtils.isEmpty(importUsers)) {
            throw new ServiceException(USER_IMPORT_LIST_IS_EMPTY);
        }
        UserImportVO respVO = UserImportVO.builder().createUsernames(new ArrayList<>())
            .updateUsernames(new ArrayList<>()).failureUsernames(new LinkedHashMap<>()).build();
        importUsers.forEach(importUser -> {
            // 校验，判断是否有不符合的原因
            try {
                checkCreateOrUpdate(null, null, importUser.getMobile(), importUser.getEmail(),
                    importUser.getDeptId(), null);
            } catch (ServiceException ex) {
                respVO.getFailureUsernames().put(importUser.getUsername(), ex.getMessage());
                return;
            }
            // 判断如果不存在，在进行插入
            User existUser = userMapper.selectByUsername(importUser.getUsername());
            if (existUser == null) {
                userMapper.insert(UserConvert.INSTANCE.convert(importUser)
                    .setPassword(encodePassword(userInitPassword))); // 设置默认密码
                respVO.getCreateUsernames().add(importUser.getUsername());
                return;
            }
            // 如果存在，判断是否允许更新
            if (!isUpdateSupport) {
                respVO.getFailureUsernames().put(importUser.getUsername(), USER_USERNAME_EXISTS.getMessage());
                return;
            }
            User updateUser = UserConvert.INSTANCE.convert(importUser);
            updateUser.setId(existUser.getId());
            userMapper.updateById(updateUser);
            respVO.getUpdateUsernames().add(importUser.getUsername());
        });
        return respVO;
    }

    @Override
    public List<User> getUsersByStatus(Integer status) {
        return userMapper.selectListByStatus(status);
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return SecurityUtils.matches(rawPassword, encodedPassword);
    }

    private void checkCreateOrUpdate(Long id, String username, String mobile, String email,
                                     Long deptId, Set<Long> postIds) {
        // 校验用户存在
        checkUserExists(id);
        // 校验用户名唯一
        checkUsernameUnique(id, username);
        // 校验手机号唯一
        checkMobileUnique(id, mobile);
        // 校验邮箱唯一
        checkEmailUnique(id, email);
        // 校验部门处于开启状态
        deptService.validDepts(Collections.singletonList(deptId));
        // 校验岗位处于开启状态
        postService.validPosts(postIds);
    }

    public void checkUserExists(Long id) {
        if (id == null) {
            return;
        }
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new ServiceException(USER_NOT_EXISTS);
        }
    }

    public void checkUsernameUnique(Long id, String username) {
        if (StringUtils.isBlank(username)) {
            return;
        }
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw new ServiceException(USER_USERNAME_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw new ServiceException(USER_USERNAME_EXISTS);
        }
    }

    public void checkMobileUnique(Long id, String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return;
        }
        User user = userMapper.selectByMobile(mobile);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw new ServiceException(USER_MOBILE_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw new ServiceException(USER_MOBILE_EXISTS);
        }
    }

    public void checkEmailUnique(Long id, String email) {
        if (StringUtils.isBlank(email)) {
            return;
        }
        User user = userMapper.selectByEmail(email);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw new ServiceException(USER_EMAIL_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw new ServiceException(USER_EMAIL_EXISTS);
        }
    }

    private String encodePassword(String password) {
        return SecurityUtils.encodePassword(password);
    }

    private void updateUserPost(UserUpdateQuery query, User updateObj) {
        Long userId = query.getId();
        Set<Long> dbPostIds = CollUtils.convertSet(userPostMapper.selectListByUserId(userId), UserPost::getPostId);
        // 计算新增和删除的岗位编号
        Set<Long> postIds = updateObj.getPostIds();
        Collection<Long> createPostIds = CollUtils.subtract(postIds, dbPostIds);
        Collection<Long> deletePostIds = CollUtils.subtract(dbPostIds, postIds);
        // 执行新增和删除。对于已经授权的菜单，不用做任何处理
        if (!CollUtils.isEmpty(createPostIds)) {
            userPostMapper.insertBatch(convertList(createPostIds,
                postId -> new UserPost().setUserId(userId).setPostId(postId)));
        }
        if (!CollUtils.isEmpty(deletePostIds)) {
            userPostMapper.deleteByUserIdAndPostId(userId, deletePostIds);
        }
    }

    public void checkOldPassword(Long id, String oldPassword) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new ServiceException(USER_NOT_EXISTS);
        }
        if (!isPasswordMatch(oldPassword, user.getPassword())) {
            throw new ServiceException(USER_PASSWORD_FAILED);
        }
    }

    /**
     * 获得部门条件：查询指定部门的子部门编号们，包括自身
     *
     * @param deptId 部门编号
     * @return 部门编号集合
     */
    private Set<Long> getDeptCondition(Long deptId) {
        if (deptId == null) {
            return Collections.emptySet();
        }
        Set<Long> deptIds = CollUtils.convertSet(deptService.getDeptsByParentIdFromCache(
            deptId, true), Department::getId);
        deptIds.add(deptId); // 包括自身
        return deptIds;
    }

}
