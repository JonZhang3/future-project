package com.future.module.system.domain.convert;

import com.future.framework.common.domain.PageResult;
import com.future.module.system.domain.entity.LoginLog;
import com.future.module.system.domain.vo.logger.LoginLogExcelVO;
import com.future.module.system.domain.vo.logger.LoginLogRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LoginLogConvert {

    LoginLogConvert INSTANCE = Mappers.getMapper(LoginLogConvert.class);

    LoginLogRespVO convert(LoginLog bean);
    
    PageResult<LoginLogRespVO> convertPage(PageResult<LoginLog> page);

    LoginLogExcelVO convertToExcel(LoginLog bean);
    
    List<LoginLogExcelVO> convertList(List<LoginLog> list);
    
}
