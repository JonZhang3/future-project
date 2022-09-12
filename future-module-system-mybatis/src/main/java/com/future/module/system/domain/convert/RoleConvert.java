package com.future.module.system.domain.convert;

import com.future.module.system.domain.entity.Role;
import com.future.module.system.domain.query.permission.RoleCreateQuery;
import com.future.module.system.domain.query.permission.RoleUpdateQuery;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleConvert {

    RoleConvert INSTANCE = Mappers.getMapper(RoleConvert.class);
    
    Role convert(RoleCreateQuery bean);

    Role convert(RoleUpdateQuery bean);
    
}
