package com.future.framework.web.service;

import com.future.common.constant.Constants;
import com.future.common.core.domain.model.LoginUser;
import com.future.common.utils.StringUtils;
import com.future.framework.config.FrameworkConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class TokenService {

    @Resource(type = FrameworkConfig.class)
    private FrameworkConfig frameworkConfig;

    public LoginUser getLoginUser(HttpServletRequest request) {
        String token = getToken(request);
        return null;
    }

    private String createToken(LoginUser loginUser) {
        return "";
    }
    
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(frameworkConfig.getJwt().getHeaderName());
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
            token = StringUtils.replaceOnce(token, Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

}
