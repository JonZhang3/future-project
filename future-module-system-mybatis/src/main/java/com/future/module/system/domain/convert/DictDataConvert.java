package com.future.module.system.domain.convert;

import com.future.module.system.domain.entity.DictData;
import com.future.module.system.domain.query.dict.DictDataCreateQuery;
import com.future.module.system.domain.query.dict.DictDataUpdateQuery;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DictDataConvert {

    DictDataConvert INSTANCE = Mappers.getMapper(DictDataConvert.class);
    
    DictData convert(DictDataUpdateQuery bean);

    DictData convert(DictDataCreateQuery bean);
    
}
