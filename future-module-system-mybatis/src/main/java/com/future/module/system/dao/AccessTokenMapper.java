package com.future.module.system.dao;

import com.future.framework.common.domain.PageResult;
import com.future.framework.mybatis.mapper.BaseMapper;
import com.future.framework.mybatis.query.LambdaQueryWrapper;
import com.future.module.system.domain.entity.AccessToken;
import com.future.module.system.domain.query.auth.AccessTokenPageQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface AccessTokenMapper extends BaseMapper<AccessToken> {

    default AccessToken selectByAccessToken(String accessToken) {
        return selectOne(AccessToken::getAccessToken, accessToken);
    }

    default List<AccessToken> selectListByRefreshToken(String refreshToken) {
        return selectList(AccessToken::getRefreshToken, refreshToken);
    }

    default PageResult<AccessToken> selectPage(AccessTokenPageQuery query) {
        return selectPage(query.getPageNo(), query.getPageSize(), new LambdaQueryWrapper<AccessToken>()
            .eqIfPresent(AccessToken::getUserId, query.getUserId())
            .eqIfPresent(AccessToken::getUserType, query.getUserType())
            .gt(AccessToken::getExpiresTime, new Date())
            .orderByDesc(AccessToken::getId));
    }

}
