package com.future.system.controller.monitor;

import com.future.common.annotation.Log;
import com.future.common.constant.enums.BusinessType;
import com.future.common.core.domain.R;
import com.future.system.domain.query.LoginInfoQuery;
import com.future.system.service.LoginInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 系统访问记录
 *
 * @author JonZhang
 */
@RestController
@RequestMapping("/api/monitor/logininfo")
public class LoginInfoController {

    @Resource(type = LoginInfoService.class)
    private LoginInfoService loginInfoService;

    @PreAuthorize("@ss.hasPermi('monitor:logininfor:list')")
    @GetMapping("/list")
    public R pageList(LoginInfoQuery query) {
        return R.ok();
    }

    @Log(title = "登录日志", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, LoginInfoQuery query) {

    }

    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
    @DeleteMapping("/{ids}")
    public R remove(@PathVariable("ids") List<Long> ids) {
        loginInfoService.deleteLoginInfoByIds(ids);
        return R.ok();
    }

    @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
    @Log(title = "登录日志", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean")
    public R clean() {
        loginInfoService.clearAllLoginInfo();
        return R.ok();
    }
    
}
