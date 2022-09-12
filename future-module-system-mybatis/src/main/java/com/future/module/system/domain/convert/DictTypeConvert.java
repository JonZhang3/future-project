package com.future.module.system.domain.convert;

import com.future.module.system.domain.entity.DictType;
import com.future.module.system.domain.query.dict.DictTypeCreateQuery;
import com.future.module.system.domain.query.dict.DictTypeUpdateQuery;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DictTypeConvert {

    DictTypeConvert INSTANCE = Mappers.getMapper(DictTypeConvert.class);
    
    DictType convert(DictTypeCreateQuery bean);

    DictType convert(DictTypeUpdateQuery bean);
    
}
