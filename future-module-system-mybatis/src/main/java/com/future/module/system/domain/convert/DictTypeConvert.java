package com.future.module.system.domain.convert;

import com.future.framework.common.domain.PageResult;
import com.future.module.system.domain.entity.DictType;
import com.future.module.system.domain.query.dict.DictTypeCreateQuery;
import com.future.module.system.domain.query.dict.DictTypeUpdateQuery;
import com.future.module.system.domain.vo.dict.DictTypeExcelVO;
import com.future.module.system.domain.vo.dict.DictTypeRespVO;
import com.future.module.system.domain.vo.dict.DictTypeSimpleRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DictTypeConvert {

    DictTypeConvert INSTANCE = Mappers.getMapper(DictTypeConvert.class);
    
    DictType convert(DictTypeCreateQuery bean);

    DictType convert(DictTypeUpdateQuery bean);

    PageResult<DictTypeRespVO> convertPage(PageResult<DictType> bean);

    DictTypeRespVO convert(DictType bean);

    List<DictTypeSimpleRespVO> convertList(List<DictType> list);

    List<DictTypeExcelVO> convertToExcelList(List<DictType> list);
    
}
