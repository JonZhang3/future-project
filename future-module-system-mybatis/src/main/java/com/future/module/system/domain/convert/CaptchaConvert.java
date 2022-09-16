package com.future.module.system.domain.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.future.module.system.domain.vo.CaptchaImageVO;

import cn.hutool.captcha.AbstractCaptcha;

@Mapper
public interface CaptchaConvert {

    CaptchaConvert INSTANCE = Mappers.getMapper(CaptchaConvert.class);

    default CaptchaImageVO convert(String uuid, AbstractCaptcha captcha) {
        return CaptchaImageVO.builder().uuid(uuid).img(captcha.getImageBase64()).build();
    }

}
