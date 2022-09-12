package com.future.module.system.service;

import com.future.framework.common.domain.PageResult;
import com.future.module.system.domain.entity.Notice;
import com.future.module.system.domain.query.notice.NoticeCreateQuery;
import com.future.module.system.domain.query.notice.NoticePageQuery;
import com.future.module.system.domain.query.notice.NoticeUpdateQuery;

/**
 * 通知公告 Service 接口
 *
 * @author JonZhang
 */
public interface NoticeService {

    /**
     * 创建岗位公告公告
     *
     * @param query 岗位公告公告信息
     * @return 岗位公告公告编号
     */
    Long createNotice(NoticeCreateQuery query);

    /**
     * 更新岗位公告公告
     *
     * @param query 岗位公告公告信息
     */
    void updateNotice(NoticeUpdateQuery query);

    /**
     * 删除岗位公告公告信息
     *
     * @param id 岗位公告公告编号
     */
    void deleteNotice(Long id);

    /**
     * 获得岗位公告公告分页列表
     *
     * @param query 分页条件
     * @return 部门分页列表
     */
    PageResult<Notice> pageNotices(NoticePageQuery query);

    /**
     * 获得岗位公告公告信息
     *
     * @param id 岗位公告公告编号
     * @return 岗位公告公告信息
     */
    Notice getNotice(Long id);

}
