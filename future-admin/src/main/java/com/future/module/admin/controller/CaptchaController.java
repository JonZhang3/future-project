package com.future.module.admin.controller;

import com.future.framework.common.domain.R;
import com.future.module.system.service.CaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;

@Api(tags = "管理后台 - 验证码")
@RestController
@RequestMapping("/system/captcha")
public class CaptchaController {

    @Resource
    private CaptchaService captchaService;

    @GetMapping("/get-image")
    @PermitAll
    @ApiOperation("生成图片验证码")
    public R getCaptchaImage() {
        return R.ok(captchaService.getCaptchaImage());
    }

}
