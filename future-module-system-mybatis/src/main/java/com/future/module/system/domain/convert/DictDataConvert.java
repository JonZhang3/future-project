package com.future.module.system.domain.convert;

import com.future.framework.common.domain.PageResult;
import com.future.module.system.domain.entity.DictData;
import com.future.module.system.domain.query.dict.DictDataCreateQuery;
import com.future.module.system.domain.query.dict.DictDataUpdateQuery;
import com.future.module.system.domain.vo.dict.DictDataExcelVO;
import com.future.module.system.domain.vo.dict.DictDataRespVO;
import com.future.module.system.domain.vo.dict.DictDataSimpleVO;
import com.future.module.system.domain.vo.dict.DictDataVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DictDataConvert {

    DictDataConvert INSTANCE = Mappers.getMapper(DictDataConvert.class);
    
    DictData convert(DictDataUpdateQuery bean);

    DictData convert(DictDataCreateQuery bean);

    DictDataSimpleVO convertToSimple(DictData bean);
    
    List<DictDataSimpleVO> convertList(List<DictData> list);

    DictDataVO convertPage(DictData bean);
    
    PageResult<DictDataVO> convertPage(PageResult<DictData> page);

    DictDataRespVO convert(DictData bean);

    DictDataExcelVO convertToExcel(DictData bean);
    
    List<DictDataExcelVO> convertToExcelList(List<DictData> bean);
    
}
