package com.future.module.system.dao;

import com.future.framework.common.domain.PageResult;
import com.future.framework.mybatis.mapper.BaseMapper;
import com.future.framework.mybatis.query.LambdaQueryWrapper;
import com.future.module.system.domain.entity.DictType;
import com.future.module.system.domain.query.dict.DictTypeExportQuery;
import com.future.module.system.domain.query.dict.DictTypePageQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DictTypeMapper extends BaseMapper<DictType> {

    default PageResult<DictType> selectPage(DictTypePageQuery query) {
        return selectPage(query.getPageNo(), query.getPageSize(), new LambdaQueryWrapper<DictType>()
            .likeIfPresent(DictType::getName, query.getName())
            .likeIfPresent(DictType::getType, query.getType())
            .eqIfPresent(DictType::getStatus, query.getStatus())
            .betweenIfPresent(DictType::getCreateTime, query.getCreateTime())
            .orderByDesc(DictType::getId));
    }

    default List<DictType> selectList(DictTypeExportQuery query) {
        return selectList(new LambdaQueryWrapper<DictType>()
            .likeIfPresent(DictType::getName, query.getName())
            .likeIfPresent(DictType::getType, query.getType())
            .eqIfPresent(DictType::getStatus, query.getStatus())
            .betweenIfPresent(DictType::getCreateTime, query.getCreateTime()));
    }

    default DictType selectByType(String type) {
        return selectOne(DictType::getType, type);
    }

    default DictType selectByName(String name) {
        return selectOne(DictType::getName, name);
    }

}
