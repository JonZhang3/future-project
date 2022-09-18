package com.future.module.admin.controller;

import com.future.framework.common.annotations.OperateLog;
import com.future.framework.common.constant.enums.CommonStatus;
import com.future.framework.common.constant.enums.Sex;
import com.future.framework.common.domain.PageResult;
import com.future.framework.common.domain.R;
import com.future.framework.common.utils.CollUtils;
import com.future.framework.common.utils.ExcelUtils;
import com.future.framework.common.utils.MapUtils;
import com.future.module.system.domain.convert.UserConvert;
import com.future.module.system.domain.entity.Department;
import com.future.module.system.domain.entity.User;
import com.future.module.system.domain.query.user.*;
import com.future.module.system.domain.vo.user.*;
import com.future.module.system.service.DeptService;
import com.future.module.system.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

import static com.future.framework.common.constant.enums.OperateType.EXPORT;

//@Api(tags = "管理后台 - 用户")
@RestController
@RequestMapping("/admin-api/system/user")
@Validated
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private DeptService deptService;

    @PostMapping("/create")
//    @ApiOperation("新增用户")
    @PreAuthorize("@ss.hasPermission('system:user:create')")
    public R<Long> createUser(@Valid @RequestBody UserCreateQuery query) {
        return R.ok(userService.createUser(query));
    }

    @PutMapping("update")
//    @ApiOperation("修改用户")
    @PreAuthorize("@ss.hasPermission('system:user:update')")
    public R<Boolean> updateUser(@Valid @RequestBody UserUpdateQuery query) {
        userService.updateUser(query);
        return R.ok(true);
    }

    @DeleteMapping("/delete")
//    @ApiOperation("删除用户")
//    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:user:delete')")
    public R<Boolean> deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return R.ok(true);
    }

    @PutMapping("/update-password")
//    @ApiOperation("重置用户密码")
    @PreAuthorize("@ss.hasPermission('system:user:update-password')")
    public R<Boolean> updateUserPassword(@Valid @RequestBody UserUpdatePasswordReqVO reqVO) {
        userService.updateUserPassword(reqVO.getId(), reqVO.getPassword());
        return R.ok(true);
    }

    @PutMapping("/update-status")
//    @ApiOperation("修改用户状态")
    @PreAuthorize("@ss.hasPermission('system:user:update')")
    public R<Boolean> updateUserStatus(@Valid @RequestBody UserUpdateStatusReqVO reqVO) {
        userService.updateUserStatus(reqVO.getId(), reqVO.getStatus());
        return R.ok(true);
    }

    @GetMapping("/page")
//    @ApiOperation("获得用户分页列表")
    @PreAuthorize("@ss.hasPermission('system:user:list')")
    public R<PageResult<UserPageItemRespVO>> getUserPage(@Valid UserPageQuery query) {
        // 获得用户分页列表
        PageResult<User> pageResult = userService.getUserPage(query);
        if (CollUtils.isEmpty(pageResult.getList())) {
            return R.ok(new PageResult<>(pageResult.getTotal())); // 返回空
        }

        // 获得拼接需要的数据
        Collection<Long> deptIds = CollUtils.convertList(pageResult.getList(), User::getDeptId);
        Map<Long, Department> deptMap = deptService.getDeptMap(deptIds);
        // 拼接结果返回
        List<UserPageItemRespVO> userList = new ArrayList<>(pageResult.getList().size());
        pageResult.getList().forEach(user -> {
            UserPageItemRespVO respVO = UserConvert.INSTANCE.convertToUserPageItem(user);
            respVO.setDept(UserConvert.INSTANCE.convertToUserPageItemDept(deptMap.get(user.getDeptId())));
            userList.add(respVO);
        });
        return R.ok(new PageResult<>(userList, pageResult.getTotal()));
    }

    @GetMapping("/list-all-simple")
//    @ApiOperation(value = "获取用户精简信息列表", notes = "只包含被开启的用户，主要用于前端的下拉选项")
    public R<List<UserSimpleRespVO>> getSimpleUsers() {
        // 获用户门列表，只要开启状态的
        List<User> list = userService.getUsersByStatus(CommonStatus.VALID.getValue());
        // 排序后，返回给前端
        return R.ok(UserConvert.INSTANCE.convertToSimpleUserList(list));
    }

    @GetMapping("/get")
//    @ApiOperation("获得用户详情")
//    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:user:query')")
    public R<UserProfileRespVO> getInfo(@RequestParam("id") Long id) {
        return R.ok(UserConvert.INSTANCE.convertToUserProfile(userService.getUser(id)));
    }

    @GetMapping("/export")
//    @ApiOperation("导出用户")
    @PreAuthorize("@ss.hasPermission('system:user:export')")
    @OperateLog(type = EXPORT)
    public void exportUsers(@Validated UserExportQuery query,
            HttpServletResponse response) throws IOException {
        // 获得用户列表
        List<User> users = userService.getUsers(query);

        // 获得拼接需要的数据
        Collection<Long> deptIds = CollUtils.convertList(users, User::getDeptId);
        Map<Long, Department> deptMap = deptService.getDeptMap(deptIds);
        Map<Long, User> deptLeaderUserMap = userService.getUserMap(
                CollUtils.convertSet(deptMap.values(), Department::getLeaderUserId));
        // 拼接数据
        List<UserExcelVO> excelUsers = new ArrayList<>(users.size());
        users.forEach(user -> {
            UserExcelVO excelVO = UserConvert.INSTANCE.convertToUserExcel(user);
            // 设置部门
            MapUtils.findAndThen(deptMap, user.getDeptId(), dept -> {
                excelVO.setDeptName(dept.getName());
                // 设置部门负责人的名字
                MapUtils.findAndThen(deptLeaderUserMap, dept.getLeaderUserId(),
                        deptLeaderUser -> excelVO.setDeptLeaderNickname(deptLeaderUser.getNickname()));
            });
            excelUsers.add(excelVO);
        });

        // 输出
        ExcelUtils.write(response, "用户数据.xls", "用户列表", UserExcelVO.class, excelUsers);
    }

    @GetMapping("/get-import-template")
//    @ApiOperation("获得导入用户模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<UserImportExcelVO> list = Arrays.asList(
                UserImportExcelVO.builder().username("Jon").deptId(1L).email("test@xxx.cn").mobile("15601691300")
                        .nickname("芋道").status(CommonStatus.VALID.getValue()).sex(Sex.MALE.getValue()).build(),
                UserImportExcelVO.builder().username("yuanma").deptId(2L).email("yuanma@iocoder.cn")
                        .mobile("15601701300")
                        .nickname("源码").status(CommonStatus.INVALID.getValue()).sex(Sex.FEMALE.getValue()).build());

        // 输出
        ExcelUtils.write(response, "用户导入模板.xls", "用户列表", UserImportExcelVO.class, list);
    }

    @PostMapping("/import")
//    @ApiOperation("导入用户")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "file", value = "Excel 文件", required = true, dataTypeClass = MultipartFile.class),
//            @ApiImplicitParam(name = "updateSupport", value = "是否支持更新，默认为 false", example = "true", dataTypeClass = Boolean.class)
//    })
    @PreAuthorize("@ss.hasPermission('system:user:import')")
    public R<UserImportVO> importExcel(@RequestParam("file") MultipartFile file,
            @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport)
            throws Exception {
        List<UserImportExcelQuery> list = ExcelUtils.read(file, UserImportExcelQuery.class);
        return R.ok(userService.importUsers(list, updateSupport));
    }

}
