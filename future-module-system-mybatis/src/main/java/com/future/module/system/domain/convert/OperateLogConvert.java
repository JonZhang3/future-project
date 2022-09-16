package com.future.module.system.domain.convert;

import com.future.framework.common.utils.MapUtils;
import com.future.module.system.domain.entity.OperationLog;
import com.future.module.system.domain.entity.User;
import com.future.module.system.domain.vo.logger.OperateLogExcelVO;
import com.future.module.system.domain.vo.logger.OperateLogRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.future.framework.common.exception.CommonErrorCode.SUCCESS;

@Mapper
public interface OperateLogConvert {

    OperateLogConvert INSTANCE = Mappers.getMapper(OperateLogConvert.class);

    OperateLogRespVO convert(OperationLog bean);

    OperateLogExcelVO convertToExcel(OperationLog bean);

    default List<OperateLogExcelVO> convertToExcelList(List<OperationLog> list, Map<Long, User> userMap) {
        return list.stream().map(operateLog -> {
            OperateLogExcelVO excelVO = convertToExcel(operateLog);
            MapUtils.findAndThen(userMap, operateLog.getUserId(), user -> excelVO.setUserNickname(user.getNickname()));
            excelVO.setSuccessStr(SUCCESS.getCode().equals(operateLog.getResultCode()) ? "成功" : "失败");
            return excelVO;
        }).collect(Collectors.toList());
    }

}
