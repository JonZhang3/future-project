package com.future.module.system.service;

import com.future.framework.common.domain.PageResult;
import com.future.module.system.domain.entity.DictType;
import com.future.module.system.domain.query.dict.DictTypeCreateQuery;
import com.future.module.system.domain.query.dict.DictTypeExportQuery;
import com.future.module.system.domain.query.dict.DictTypePageQuery;
import com.future.module.system.domain.query.dict.DictTypeUpdateQuery;

import java.util.List;

/**
 * 字典类型 Service 接口
 *
 * @author JonZhang
 */
public interface DictTypeService {

    /**
     * 创建字典类型
     *
     * @param query 字典类型信息
     * @return 字典类型编号
     */
    Long createDictType(DictTypeCreateQuery query);

    /**
     * 更新字典类型
     *
     * @param reqVO 字典类型信息
     */
    void updateDictType(DictTypeUpdateQuery query);

    /**
     * 删除字典类型
     *
     * @param id 字典类型编号
     */
    void deleteDictType(Long id);

    /**
     * 获得字典类型分页列表
     *
     * @param query 分页请求
     * @return 字典类型分页列表
     */
    PageResult<DictType> getDictTypePage(DictTypePageQuery query);

    /**
     * 获得字典类型列表
     *
     * @param query 列表请求
     * @return 字典类型列表
     */
    List<DictType> getDictTypeList(DictTypeExportQuery query);

    /**
     * 获得字典类型详情
     *
     * @param id 字典类型编号
     * @return 字典类型
     */
    DictType getDictType(Long id);

    /**
     * 获得字典类型详情
     *
     * @param type 字典类型
     * @return 字典类型详情
     */
    DictType getDictType(String type);

    /**
     * 获得全部字典类型列表
     *
     * @return 字典类型列表
     */
    List<DictType> getDictTypeList();

}
