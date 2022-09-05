package com.future.system.controller.monitor;

import com.future.common.core.domain.R;
import com.future.system.domain.query.LoginInfoQuery;
import com.future.system.service.LoginInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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

    @PreAuthorize("@ss.hasPermi('monitor:logininfor:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response) {
        
    }
    
}
