package com.future.module.system.dao;

import com.future.framework.common.domain.PageResult;
import com.future.framework.mybatis.mapper.BaseMapper;
import com.future.framework.mybatis.query.LambdaQueryWrapper;
import com.future.module.system.domain.entity.DictData;
import com.future.module.system.domain.query.dict.DictDataExportQuery;
import com.future.module.system.domain.query.dict.DictDataPageQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Mapper
public interface DictDataMapper extends BaseMapper<DictData> {

    default DictData selectByDictTypeAndValue(String dictType, String value) {
        return selectOne(new LambdaQueryWrapper<DictData>().eq(DictData::getDictType, dictType)
            .eq(DictData::getValue, value));
    }

    default DictData selectByDictTypeAndLabel(String dictType, String label) {
        return selectOne(new LambdaQueryWrapper<DictData>().eq(DictData::getDictType, dictType)
            .eq(DictData::getLabel, label));
    }

    default List<DictData> selectByDictTypeAndValues(String dictType, Collection<String> values) {
        return selectList(new LambdaQueryWrapper<DictData>().eq(DictData::getDictType, dictType)
            .in(DictData::getValue, values));
    }

    default long selectCountByDictType(String dictType) {
        return selectCount(DictData::getDictType, dictType);
    }

    default PageResult<DictData> selectPage(DictDataPageQuery query) {
        return selectPage(query.getPageNo(), query.getPageSize(), new LambdaQueryWrapper<DictData>()
            .likeIfPresent(DictData::getLabel, query.getLabel())
            .likeIfPresent(DictData::getDictType, query.getDictType())
            .eqIfPresent(DictData::getStatus, query.getStatus())
            .orderByDesc(Arrays.asList(DictData::getDictType, DictData::getSort)));
    }

    default List<DictData> selectList(DictDataExportQuery query) {
        return selectList(new LambdaQueryWrapper<DictData>()
            .likeIfPresent(DictData::getLabel, query.getLabel())
            .likeIfPresent(DictData::getDictType, query.getDictType())
            .eqIfPresent(DictData::getStatus, query.getStatus()));
    }

}
