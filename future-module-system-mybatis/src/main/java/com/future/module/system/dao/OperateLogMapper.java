package com.future.module.system.dao;

import com.future.framework.common.domain.PageResult;
import com.future.framework.common.exception.CommonErrorCode;
import com.future.framework.mybatis.mapper.BaseMapper;
import com.future.framework.mybatis.query.LambdaQueryWrapper;
import com.future.module.system.domain.entity.OperationLog;
import com.future.module.system.domain.query.logger.OperateLogExportQuery;
import com.future.module.system.domain.query.logger.OperateLogPageQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface OperateLogMapper extends BaseMapper<OperationLog> {

    default PageResult<OperationLog> selectPage(OperateLogPageQuery reqVO, Collection<Long> userIds) {
        LambdaQueryWrapper<OperationLog> query = new LambdaQueryWrapper<OperationLog>()
            .likeIfPresent(OperationLog::getModule, reqVO.getModule())
            .inIfPresent(OperationLog::getUserId, userIds)
            .eqIfPresent(OperationLog::getType, reqVO.getType())
            .betweenIfPresent(OperationLog::getStartTime, reqVO.getStartTime());
        if (Boolean.TRUE.equals(reqVO.getSuccess())) {
            query.eq(OperationLog::getResultCode, CommonErrorCode.SUCCESS.getCode());
        } else if (Boolean.FALSE.equals(reqVO.getSuccess())) {
            query.gt(OperationLog::getResultCode, CommonErrorCode.SUCCESS.getCode());
        }
        query.orderByDesc(OperationLog::getId); // 降序
        return selectPage(reqVO.getPageNo(), reqVO.getPageSize(), query);
    }

    default List<OperationLog> selectList(OperateLogExportQuery reqVO, Collection<Long> userIds) {
        LambdaQueryWrapper<OperationLog> query = new LambdaQueryWrapper<OperationLog>()
            .likeIfPresent(OperationLog::getModule, reqVO.getModule())
            .inIfPresent(OperationLog::getUserId, userIds)
            .eqIfPresent(OperationLog::getType, reqVO.getType())
            .betweenIfPresent(OperationLog::getStartTime, reqVO.getStartTime());
        if (Boolean.TRUE.equals(reqVO.getSuccess())) {
            query.eq(OperationLog::getResultCode, CommonErrorCode.SUCCESS.getCode());
        } else if (Boolean.FALSE.equals(reqVO.getSuccess())) {
            query.gt(OperationLog::getResultCode, CommonErrorCode.SUCCESS.getCode());
        }
        query.orderByDesc(OperationLog::getId); // 降序
        return selectList(query);
    }

}
