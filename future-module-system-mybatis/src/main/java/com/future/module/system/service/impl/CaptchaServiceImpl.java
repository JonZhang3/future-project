package com.future.module.system.service.impl;

import com.future.framework.common.utils.IdUtils;
import com.future.module.system.domain.convert.CaptchaConvert;
import com.future.module.system.domain.vo.CaptchaImageVO;
import com.future.module.system.framework.config.CaptchaProperties;
import com.future.module.system.service.CaptchaService;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Resource
    private CaptchaProperties captchaProperties;

    @Override
    public CaptchaImageVO getCaptchaImage() {
        boolean enable = captchaProperties.getEnable();
        if (!enable) {
            return CaptchaImageVO.builder().enable(enable).build();
        }
        // 生成验证码
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(captchaProperties.getWidth(),
                captchaProperties.getHeight());
        String uuid = IdUtils.fastSimpleUUID();
        // TODO save to cache
        // captchaRedisDAO.set(uuid, captcha.getCode(), captchaProperties.getTimeout());
        return CaptchaConvert.INSTANCE.convert(uuid, captcha).setEnable(enable);
    }

    @Override
    public Boolean isCaptchaEnable() {
        return captchaProperties.getEnable();
    }

    @Override
    public String getCaptchaCode(String uuid) {
        // TODO from cache
        return null;
    }

    @Override
    public void deleteCaptchaCode(String uuid) {
        // TODO delete from cache
    }
}
