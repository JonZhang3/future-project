package com.future.module.system.dao;

import com.future.framework.common.constant.enums.LoginResult;
import com.future.framework.common.domain.PageResult;
import com.future.framework.mybatis.mapper.BaseMapper;
import com.future.framework.mybatis.query.LambdaQueryWrapper;
import com.future.module.system.domain.entity.LoginLog;
import com.future.module.system.domain.query.logger.LoginLogExportQuery;
import com.future.module.system.domain.query.logger.LoginLogPageQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    default PageResult<LoginLog> selectPage(LoginLogPageQuery q) {
        LambdaQueryWrapper<LoginLog> query = new LambdaQueryWrapper<LoginLog>()
            .likeIfPresent(LoginLog::getUserIp, q.getUserIp())
            .likeIfPresent(LoginLog::getUsername, q.getUsername())
            .betweenIfPresent(LoginLog::getCreateTime, q.getCreateTime());
        if (Boolean.TRUE.equals(q.getStatus())) {
            query.eq(LoginLog::getResult, LoginResult.SUCCESS.getValue());
        } else if (Boolean.FALSE.equals(q.getStatus())) {
            query.gt(LoginLog::getResult, LoginResult.SUCCESS.getValue());
        }
        query.orderByDesc(LoginLog::getId); // 降序
        return selectPage(q.getPageNo(), q.getPageSize(), query);
    }

    default List<LoginLog> selectList(LoginLogExportQuery q) {
        LambdaQueryWrapper<LoginLog> query = new LambdaQueryWrapper<LoginLog>()
            .likeIfPresent(LoginLog::getUserIp, q.getUserIp())
            .likeIfPresent(LoginLog::getUsername, q.getUsername())
            .betweenIfPresent(LoginLog::getCreateTime, q.getCreateTime());
        if (Boolean.TRUE.equals(q.getStatus())) {
            query.eq(LoginLog::getResult, LoginResult.SUCCESS.getValue());
        } else if (Boolean.FALSE.equals(q.getStatus())) {
            query.gt(LoginLog::getResult, LoginResult.SUCCESS.getValue());
        }
        query.orderByDesc(LoginLog::getId); // 降序
        return selectList(query);
    }

}
