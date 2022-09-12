package com.future.module.system.domain.convert;

import com.future.module.system.domain.entity.Notice;
import com.future.module.system.domain.query.notice.NoticeCreateQuery;
import com.future.module.system.domain.query.notice.NoticeUpdateQuery;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NoticeConvert {

    NoticeConvert INSTANCE = Mappers.getMapper(NoticeConvert.class);
    
    Notice convert(NoticeUpdateQuery bean);

    Notice convert(NoticeCreateQuery bean);
    
}
