package com.future.module.system.dao;

import com.future.framework.common.domain.PageResult;
import com.future.framework.mybatis.mapper.BaseMapper;
import com.future.framework.mybatis.query.LambdaQueryWrapper;
import com.future.module.system.domain.entity.Notice;
import com.future.module.system.domain.query.notice.NoticePageQuery;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

    default PageResult<Notice> selectPage(NoticePageQuery query) {
        return selectPage(query.getPageNo(), query.getPageSize(), new LambdaQueryWrapper<Notice>()
            .likeIfPresent(Notice::getTitle, query.getTitle())
            .eqIfPresent(Notice::getStatus, query.getStatus())
            .orderByDesc(Notice::getId));
    }

}
