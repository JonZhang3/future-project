package com.future.module.system.service.impl;

import com.future.framework.common.domain.PageResult;
import com.future.framework.common.exception.ServiceException;
import com.future.module.system.dao.NoticeMapper;
import com.future.module.system.domain.convert.NoticeConvert;
import com.future.module.system.domain.entity.Notice;
import com.future.module.system.domain.query.notice.NoticeCreateQuery;
import com.future.module.system.domain.query.notice.NoticePageQuery;
import com.future.module.system.domain.query.notice.NoticeUpdateQuery;
import com.future.module.system.service.NoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.future.module.system.constants.enums.SystemErrorCode.NOTICE_NOT_FOUND;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    @Override
    public Long createNotice(NoticeCreateQuery query) {
        Notice notice = NoticeConvert.INSTANCE.convert(query);
        noticeMapper.insert(notice);
        return notice.getId();
    }

    @Override
    public void updateNotice(NoticeUpdateQuery query) {
        // 校验是否存在
        this.checkNoticeExists(query.getId());
        // 更新通知公告
        Notice updateObj = NoticeConvert.INSTANCE.convert(query);
        noticeMapper.updateById(updateObj);
    }

    @Override
    public void deleteNotice(Long id) {
        // 校验是否存在
        this.checkNoticeExists(id);
        // 删除通知公告
        noticeMapper.deleteById(id);
    }

    @Override
    public PageResult<Notice> pageNotices(NoticePageQuery query) {
        return noticeMapper.selectPage(query);
    }

    @Override
    public Notice getNotice(Long id) {
        return noticeMapper.selectById(id);
    }

    public void checkNoticeExists(Long id) {
        if (id == null) {
            return;
        }
        Notice notice = noticeMapper.selectById(id);
        if (notice == null) {
            throw new ServiceException(NOTICE_NOT_FOUND);
        }
    }

}
