package com.future.module.system.service;

import com.future.framework.common.domain.PageResult;
import com.future.module.system.domain.entity.DictData;
import com.future.module.system.domain.query.dict.DictDataCreateQuery;
import com.future.module.system.domain.query.dict.DictDataExportQuery;
import com.future.module.system.domain.query.dict.DictDataPageQuery;
import com.future.module.system.domain.query.dict.DictDataUpdateQuery;

import java.util.Collection;
import java.util.List;

public interface DictDataService {

    /**
     * 创建字典数据
     *
     * @param query 字典数据信息
     * @return 字典数据编号
     */
    Long createDictData(DictDataCreateQuery query);

    /**
     * 更新字典数据
     *
     * @param query 字典数据信息
     */
    void updateDictData(DictDataUpdateQuery query);

    /**
     * 删除字典数据
     *
     * @param id 字典数据编号
     */
    void deleteDictData(Long id);

    /**
     * 获得字典数据列表
     *
     * @return 字典数据全列表
     */
    List<DictData> getDictDatas();

    /**
     * 获得字典数据分页列表
     *
     * @param query 分页请求
     * @return 字典数据分页列表
     */
    PageResult<DictData> getDictDataPage(DictDataPageQuery query);

    /**
     * 获得字典数据列表
     *
     * @param query 列表请求
     * @return 字典数据列表
     */
    List<DictData> getDictDatas(DictDataExportQuery query);

    /**
     * 获得字典数据详情
     *
     * @param id 字典数据编号
     * @return 字典数据
     */
    DictData getDictData(Long id);

    /**
     * 获得指定字典类型的数据数量
     *
     * @param dictType 字典类型
     * @return 数据数量
     */
    long countByDictType(String dictType);

    /**
     * 校验字典数据们是否有效。如下情况，视为无效：
     * 1. 字典数据不存在
     * 2. 字典数据被禁用
     *
     * @param dictType 字典类型
     * @param values   字典数据值的数组
     */
    void validDictDatas(String dictType, Collection<String> values);

    /**
     * 获得指定的字典数据
     *
     * @param dictType 字典类型
     * @param value    字典数据值
     * @return 字典数据
     */
    DictData getDictData(String dictType, String value);

    /**
     * 解析获得指定的字典数据，从缓存中
     *
     * @param dictType 字典类型
     * @param label    字典数据标签
     * @return 字典数据
     */
    DictData parseDictData(String dictType, String label);

}
