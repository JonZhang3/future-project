package com.future.module.system.domain.convert;

import com.future.framework.common.domain.PageResult;
import com.future.module.system.domain.entity.Post;
import com.future.module.system.domain.query.dept.PostCreateQuery;
import com.future.module.system.domain.query.dept.PostUpdateQuery;
import com.future.module.system.domain.vo.dept.PostExcelVO;
import com.future.module.system.domain.vo.dept.PostRespVO;
import com.future.module.system.domain.vo.dept.PostSimpleRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PostConvert {

    PostConvert INSTANCE = Mappers.getMapper(PostConvert.class);
    
    Post convert(PostCreateQuery bean);

    Post convert(PostUpdateQuery bean);

    PostRespVO convert(Post id);

    List<PostSimpleRespVO> convertToSimpleList(List<Post> list);

    PageResult<PostRespVO> convertPage(PageResult<Post> page);

    List<PostExcelVO> convertToPostExcelList(List<Post> list);
    
}
