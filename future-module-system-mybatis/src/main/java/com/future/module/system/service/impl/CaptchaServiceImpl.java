package com.future.module.system.service.impl;

import com.future.framework.common.utils.IdUtils;
import com.future.module.system.domain.convert.CaptchaConvert;
import com.future.module.system.domain.vo.CaptchaImageVO;
import com.future.module.system.framework.config.CaptchaProperties;
import com.future.module.system.service.CaptchaService;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    private static final String CACHE_NAME = "$Captcha$";

    @Resource
    private CaptchaProperties captchaProperties;

    @Autowired(required = false)
    private CacheManager cacheManager;

    @Override
    public CaptchaImageVO getCaptchaImage() {
        boolean enable = captchaProperties.getEnable();
        if (!enable) {
            return CaptchaImageVO.builder().enable(enable).build();
        }
        // 生成验证码
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(captchaProperties.getWidth(),
                captchaProperties.getHeight());
        captcha.createCode();
        String uuid = IdUtils.fastSimpleUUID();
        cacheManager.getCache(CACHE_NAME).put(uuid, captcha.getCode());
        return CaptchaConvert.INSTANCE.convert(uuid, captcha).setEnable(enable);
    }

    @Override
    public Boolean isCaptchaEnable() {
        return captchaProperties.getEnable();
    }

    @Override
    public String getCaptchaCode(String uuid) {
        return cacheManager.getCache(CACHE_NAME).get(uuid, String.class);
    }

    @Override
    public void deleteCaptchaCode(String uuid) {
        cacheManager.getCache(CACHE_NAME).evict(uuid);
    }
}
