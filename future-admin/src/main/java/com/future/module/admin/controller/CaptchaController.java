package com.future.module.admin.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.future.framework.common.domain.R;
import com.future.module.system.domain.vo.CaptchaImageVO;
import com.future.module.system.service.CaptchaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

//@Api(tags = "管理后台 - 验证码")
@RestController
@RequestMapping("/admin-api/system/captcha")
public class CaptchaController {

    @Resource
    private CaptchaService captchaService;

    @GetMapping("/get-image")
    @SaIgnore
//    @ApiOperation("生成图片验证码")
    public R<CaptchaImageVO> getCaptchaImage() {
        return R.ok(captchaService.getCaptchaImage());
    }

}
