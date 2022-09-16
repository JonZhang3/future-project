package com.future.module.admin.controller;

import com.future.framework.common.domain.R;
import com.future.framework.common.exception.ServiceException;
import com.future.framework.security.util.SecurityUtils;
import com.future.module.system.constants.enums.SystemErrorCode;
import com.future.module.system.domain.convert.UserConvert;
import com.future.module.system.domain.entity.Department;
import com.future.module.system.domain.entity.Post;
import com.future.module.system.domain.entity.Role;
import com.future.module.system.domain.entity.User;
import com.future.module.system.domain.query.user.UserProfileUpdatePasswordQuery;
import com.future.module.system.domain.query.user.UserProfileUpdateQuery;
import com.future.module.system.domain.vo.user.UserProfileRespVO;
import com.future.module.system.service.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

//@Api(tags = "管理后台 - 用户个人中心")
@RestController
@RequestMapping("/admin-api/system/user/profile")
@Validated
public class UserProfileController {

    @Resource
    private UserService userService;
    @Resource
    private DeptService deptService;
    @Resource
    private PostService postService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RoleService roleService;

    @GetMapping("/get")
//    @ApiOperation("获得登录用户信息")
    // @DataPermission(enable = false) // 关闭数据权限，避免只查看自己时，查询不到部门。
    public R<UserProfileRespVO> profile() {
        // 获得用户基本信息
        User user = userService.getUser(SecurityUtils.getLoginUserId());
        UserProfileRespVO resp = UserConvert.INSTANCE.convertToUserProfile(user);
        // 获得用户角色
        List<Role> userRoles = roleService.getRolesFromCache(permissionService.getUserRoleIdListByUserId(user.getId()));
        resp.setRoles(UserConvert.INSTANCE.convertToRoleList(userRoles));
        // 获得部门信息
        if (user.getDeptId() != null) {
            Department dept = deptService.getDept(user.getDeptId());
            resp.setDept(UserConvert.INSTANCE.convertToDept(dept));
        }
        // 获得岗位信息
        if (CollectionUtils.isNotEmpty(user.getPostIds())) {
            List<Post> posts = postService.getPosts(user.getPostIds());
            resp.setPosts(UserConvert.INSTANCE.convertToPostList(posts));
        }
        return R.ok(resp);
    }

    @PutMapping("/update")
//    @ApiOperation("修改用户个人信息")
    public R<Boolean> updateUserProfile(@Valid @RequestBody UserProfileUpdateQuery query) {
        userService.updateUserProfile(SecurityUtils.getLoginUserId(), query);
        return R.ok(true);
    }

    @PutMapping("/update-password")
//    @ApiOperation("修改用户个人密码")
    public R<Boolean> updateUserProfilePassword(@Valid @RequestBody UserProfileUpdatePasswordQuery query) {
        userService.updateUserPassword(SecurityUtils.getLoginUserId(), query);
        return R.ok(true);
    }

    @RequestMapping(value = "/update-avatar", method = { RequestMethod.POST, RequestMethod.PUT })
    // 解决 uni-app 不支持 Put 上传文件的问题
//    @ApiOperation("上传用户个人头像")
    public R<String> updateUserAvatar(@RequestParam("avatarFile") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new ServiceException(SystemErrorCode.FILE_IS_EMPTY);
        }
        String avatar = userService.updateUserAvatar(SecurityUtils.getLoginUserId(), file.getInputStream());
        return R.ok(avatar);
    }

}
