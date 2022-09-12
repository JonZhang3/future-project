package com.future.module.system.service.impl;

import com.future.module.system.domain.vo.CaptchaImageVO;
import com.future.module.system.framework.config.CaptchaProperties;
import com.future.module.system.service.CaptchaService;

import javax.annotation.Resource;

public class CaptchaServiceImpl implements CaptchaService {

    @Resource
    private CaptchaProperties captchaProperties;
    
    @Override
    public CaptchaImageVO getCaptchaImage() {
        return null;
    }

    @Override
    public Boolean isCaptchaEnable() {
        return captchaProperties.getEnable();
    }

    @Override
    public String getCaptchaCode(String uuid) {
        return null;
    }

    @Override
    public void deleteCaptchaCode(String uuid) {

    }
}
