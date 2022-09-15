package com.future.module.system.service.impl;

import com.future.framework.common.domain.PageResult;
import com.future.framework.common.utils.CollUtils;
import com.future.framework.common.utils.StringUtils;
import com.future.module.system.dao.OperateLogMapper;
import com.future.module.system.domain.entity.AdminUser;
import com.future.module.system.domain.entity.OperationLog;
import com.future.module.system.domain.query.logger.OperateLogExportQuery;
import com.future.module.system.domain.query.logger.OperateLogPageQuery;
import com.future.module.system.service.UserService;
import com.future.module.system.service.OperateLogService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.future.module.system.domain.entity.OperationLog.JAVA_METHOD_ARGS_MAX_LENGTH;
import static com.future.module.system.domain.entity.OperationLog.RESULT_MAX_LENGTH;

@Service
public class OperateLogServiceImpl implements OperateLogService {

    @Resource
    private OperateLogMapper operateLogMapper;

    @Resource
    private UserService userService;

    @Override
    public void createOperateLog(OperationLog log) {
        log.setJavaMethodArgs(StringUtils.maxLength(log.getJavaMethodArgs(), JAVA_METHOD_ARGS_MAX_LENGTH));
        log.setResultData(StringUtils.maxLength(log.getResultData(), RESULT_MAX_LENGTH));
        operateLogMapper.insert(log);
    }

    @Override
    public PageResult<OperationLog> getOperateLogPage(OperateLogPageQuery query) {
        // 处理基于用户昵称的查询
        Collection<Long> userIds = null;
        if (StringUtils.isNotEmpty(query.getUserNickname())) {
            userIds = CollUtils.convertSet(userService.getUsersByNickname(query.getUserNickname()), AdminUser::getId);
            if (CollectionUtils.isEmpty(userIds)) {
                return PageResult.empty();
            }
        }
        // 查询分页
        return operateLogMapper.selectPage(query, userIds);
    }

    @Override
    public List<OperationLog> getOperateLogs(OperateLogExportQuery query) {
        // 处理基于用户昵称的查询
        Collection<Long> userIds = null;
        if (StringUtils.isNotEmpty(query.getUserNickname())) {
            userIds = CollUtils.convertSet(userService.getUsersByNickname(query.getUserNickname()), AdminUser::getId);
            if (CollectionUtils.isEmpty(userIds)) {
                return Collections.emptyList();
            }
        }
        // 查询列表
        return operateLogMapper.selectList(query, userIds);
    }
}
