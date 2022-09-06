package com.future.system.controller;

import com.future.common.core.domain.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/system/dept")
public class DepartmentController {
    
    public R list() {
        return R.ok();
    }
    
}
