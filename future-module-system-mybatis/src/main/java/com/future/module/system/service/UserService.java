package com.future.module.system.service;

import com.future.framework.common.domain.PageResult;
import com.future.module.system.domain.entity.User;
import com.future.module.system.domain.query.user.*;
import com.future.module.system.domain.vo.user.UserImportVO;
import org.apache.commons.collections4.CollectionUtils;

import javax.validation.Valid;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 后台用户 Service 接口
 *
 * @author JonZhang
 */
public interface UserService {

    /**
     * 创建用户
     *
     * @param query 用户信息
     * @return 用户编号
     */
    Long createUser(@Valid UserCreateQuery query);

    /**
     * 修改用户
     *
     * @param query 用户信息
     */
    void updateUser(@Valid UserUpdateQuery query);

    /**
     * 更新用户的最后登陆信息
     *
     * @param id      用户编号
     * @param loginIp 登陆 IP
     */
    void updateUserLogin(Long id, String loginIp);

    /**
     * 修改用户个人信息
     *
     * @param id    用户编号
     * @param query 用户个人信息
     */
    void updateUserProfile(Long id, @Valid UserProfileUpdateQuery query);

    /**
     * 修改用户个人密码
     *
     * @param id    用户编号
     * @param query 更新用户个人密码
     */
    void updateUserPassword(Long id, @Valid UserProfileUpdatePasswordQuery query);

    /**
     * 更新用户头像
     *
     * @param id         用户 id
     * @param avatarFile 头像文件
     */
    String updateUserAvatar(Long id, InputStream avatarFile) throws Exception;

    /**
     * 修改密码
     *
     * @param id       用户编号
     * @param password 密码
     */
    void updateUserPassword(Long id, String password);

    /**
     * 修改状态
     *
     * @param id     用户编号
     * @param status 状态
     */
    void updateUserStatus(Long id, Integer status);

    /**
     * 删除用户
     *
     * @param id 用户编号
     */
    void deleteUser(Long id);

    /**
     * 通过用户名查询用户
     *
     * @param username 用户名
     * @return 用户对象信息
     */
    User getUserByUsername(String username);

    /**
     * 通过手机号获取用户
     *
     * @param mobile 手机号
     * @return 用户对象信息
     */
    User getUserByMobile(String mobile);

    /**
     * 获得用户分页列表
     *
     * @param query 分页条件
     * @return 分页列表
     */
    PageResult<User> getUserPage(UserPageQuery query);

    /**
     * 通过用户 ID 查询用户
     *
     * @param id 用户ID
     * @return 用户对象信息
     */
    User getUser(Long id);

    /**
     * 获得指定部门的用户数组
     *
     * @param deptIds 部门数组
     * @return 用户数组
     */
    List<User> getUsersByDeptIds(Collection<Long> deptIds);

    /**
     * 获得指定岗位的用户数组
     *
     * @param postIds 岗位数组
     * @return 用户数组
     */
    List<User> getUsersByPostIds(Collection<Long> postIds);

    /**
     * 获得用户列表
     *
     * @param ids 用户编号数组
     * @return 用户列表
     */
    List<User> getUsers(Collection<Long> ids);

    /**
     * 校验用户们是否有效。如下情况，视为无效：
     * 1. 用户编号不存在
     * 2. 用户被禁用
     *
     * @param ids 用户编号数组
     */
    void validUsers(Set<Long> ids);

    /**
     * 获得用户 Map
     *
     * @param ids 用户编号数组
     * @return 用户 Map
     */
    default Map<Long, User> getUserMap(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyMap();
        }
        List<User> users = getUsers(ids);
        return users.stream().collect(Collectors.toMap(User::getId, v -> v));
    }

    /**
     * 获得用户列表
     *
     * @param query 列表请求
     * @return 用户列表
     */
    List<User> getUsers(UserExportQuery query);

    /**
     * 获得用户列表，基于昵称模糊匹配
     *
     * @param nickname 昵称
     * @return 用户列表
     */
    List<User> getUsersByNickname(String nickname);

    /**
     * 获得用户列表，基于用户账号模糊匹配
     *
     * @param username 用户账号
     * @return 用户列表
     */
    List<User> getUsersByUsername(String username);

    /**
     * 批量导入用户
     *
     * @param importUsers     导入用户列表
     * @param isUpdateSupport 是否支持更新
     * @return 导入结果
     */
    UserImportVO importUsers(List<UserImportExcelQuery> importUsers, boolean isUpdateSupport);

    /**
     * 获得指定状态的用户们
     *
     * @param status 状态
     * @return 用户们
     */
    List<User> getUsersByStatus(Integer status);

    /**
     * 判断密码是否匹配
     *
     * @param rawPassword     未加密的密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    boolean isPasswordMatch(String rawPassword, String encodedPassword);

}
