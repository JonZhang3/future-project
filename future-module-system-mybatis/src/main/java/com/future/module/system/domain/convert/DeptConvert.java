package com.future.module.system.domain.convert;

import com.future.module.system.domain.entity.Department;
import com.future.module.system.domain.query.dept.DeptCreateQuery;
import com.future.module.system.domain.query.dept.DeptUpdateQuery;
import com.future.module.system.domain.vo.dept.DeptSimpleRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DeptConvert {

    DeptConvert INSTANCE = Mappers.getMapper(DeptConvert.class);
    
    Department convert(DeptCreateQuery query);

    Department convert(DeptUpdateQuery query);

    List<DeptSimpleRespVO> convertToSimpleList(List<Department> list);
    
}
