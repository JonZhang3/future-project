package com.future.module.system.domain.convert;

import com.future.module.system.domain.entity.Role;
import com.future.module.system.domain.query.permission.RoleCreateQuery;
import com.future.module.system.domain.query.permission.RoleUpdateQuery;
import com.future.module.system.domain.vo.permission.RoleExcelVO;
import com.future.module.system.domain.vo.permission.RoleRespVO;
import com.future.module.system.domain.vo.permission.RoleSimpleRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoleConvert {

    RoleConvert INSTANCE = Mappers.getMapper(RoleConvert.class);
    
    Role convert(RoleCreateQuery bean);

    Role convert(RoleUpdateQuery bean);

    RoleRespVO convert(Role bean);

    List<RoleSimpleRespVO> convertToSimpleRoleList(List<Role> list);

    List<RoleExcelVO> convertToRoleExcelList(List<Role> list);
    
}
