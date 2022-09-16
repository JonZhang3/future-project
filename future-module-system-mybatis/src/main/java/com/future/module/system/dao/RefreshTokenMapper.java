package com.future.module.system.dao;

import com.future.framework.mybatis.mapper.BaseMapper;
import com.future.framework.mybatis.query.LambdaQueryWrapper;
import com.future.module.system.domain.entity.RefreshToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefreshTokenMapper extends BaseMapper<RefreshToken> {

    default int deleteByRefreshToken(String refreshToken) {
        return delete(new LambdaQueryWrapper<RefreshToken>()
            .eq(RefreshToken::getRefreshToken, refreshToken));
    }

    default RefreshToken selectByRefreshToken(String refreshToken) {
        return selectOne(RefreshToken::getRefreshToken, refreshToken);
    }

}
