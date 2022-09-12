package com.future.module.system.dao;

import com.future.framework.common.domain.PageResult;
import com.future.framework.common.exception.CommonErrorCode;
import com.future.framework.mybatis.mapper.BaseMapper;
import com.future.framework.mybatis.query.LambdaQueryWrapper;
import com.future.module.system.domain.entity.OperateLog;
import com.future.module.system.domain.query.logger.OperateLogExportQuery;
import com.future.module.system.domain.query.logger.OperateLogPageQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface OperateLogMapper extends BaseMapper<OperateLog> {

    default PageResult<OperateLog> selectPage(OperateLogPageQuery reqVO, Collection<Long> userIds) {
        LambdaQueryWrapper<OperateLog> query = new LambdaQueryWrapper<OperateLog>()
            .likeIfPresent(OperateLog::getModule, reqVO.getModule())
            .inIfPresent(OperateLog::getUserId, userIds)
            .eqIfPresent(OperateLog::getType, reqVO.getType())
            .betweenIfPresent(OperateLog::getStartTime, reqVO.getStartTime());
        if (Boolean.TRUE.equals(reqVO.getSuccess())) {
            query.eq(OperateLog::getResultCode, CommonErrorCode.SUCCESS.getCode());
        } else if (Boolean.FALSE.equals(reqVO.getSuccess())) {
            query.gt(OperateLog::getResultCode, CommonErrorCode.SUCCESS.getCode());
        }
        query.orderByDesc(OperateLog::getId); // 降序
        return selectPage(reqVO.getPageNo(), reqVO.getPageSize(), query);
    }

    default List<OperateLog> selectList(OperateLogExportQuery reqVO, Collection<Long> userIds) {
        LambdaQueryWrapper<OperateLog> query = new LambdaQueryWrapper<OperateLog>()
            .likeIfPresent(OperateLog::getModule, reqVO.getModule())
            .inIfPresent(OperateLog::getUserId, userIds)
            .eqIfPresent(OperateLog::getType, reqVO.getType())
            .betweenIfPresent(OperateLog::getStartTime, reqVO.getStartTime());
        if (Boolean.TRUE.equals(reqVO.getSuccess())) {
            query.eq(OperateLog::getResultCode, CommonErrorCode.SUCCESS.getCode());
        } else if (Boolean.FALSE.equals(reqVO.getSuccess())) {
            query.gt(OperateLog::getResultCode, CommonErrorCode.SUCCESS.getCode());
        }
        query.orderByDesc(OperateLog::getId); // 降序
        return selectList(query);
    }

}
