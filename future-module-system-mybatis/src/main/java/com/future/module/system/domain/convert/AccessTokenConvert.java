package com.future.module.system.domain.convert;

import com.future.framework.security.domain.AccessTokenCheckResult;
import com.future.module.system.domain.entity.AccessToken;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccessTokenConvert {

    AccessTokenConvert INSTANCE = Mappers.getMapper(AccessTokenConvert.class);
    
    AccessTokenCheckResult convert(AccessToken bean);
    
}
