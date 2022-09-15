package com.future.module.system.domain.convert;

import com.future.framework.common.domain.PageResult;
import com.future.module.system.domain.entity.Notice;
import com.future.module.system.domain.query.notice.NoticeCreateQuery;
import com.future.module.system.domain.query.notice.NoticeUpdateQuery;
import com.future.module.system.domain.vo.notice.NoticeRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NoticeConvert {

    NoticeConvert INSTANCE = Mappers.getMapper(NoticeConvert.class);
    
    Notice convert(NoticeUpdateQuery bean);

    Notice convert(NoticeCreateQuery bean);

    PageResult<NoticeRespVO> convertPage(PageResult<Notice> page);

    NoticeRespVO convert(Notice bean);
    
}
