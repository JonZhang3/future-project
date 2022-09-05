package com.future.system.controller;

import com.future.common.core.domain.R;
import com.future.system.domain.query.UserQuery;
import com.future.system.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户管理
 * 
 * @author JonZhang
 */
@RestController
@RequestMapping("/api/sysytem/user")
public class UserController {

    @Resource(type = UserService.class)
    private UserService userService;

    @GetMapping("/list")
    public R pageList(UserQuery dto) {
        return R.ok(userService.pageListUsers(dto));
    }

    /**
     * 修改用户信息
     */
    @PutMapping
    public R updateUser(@RequestBody UserQuery dto) {
        
        return R.ok();
    }
    
//    @PreAuthorize("")
//    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R deleteUser(@PathVariable("ids") List<Long> ids) {
        userService.deleteUsersByIds(ids);
        return R.ok();
    }
    
}
