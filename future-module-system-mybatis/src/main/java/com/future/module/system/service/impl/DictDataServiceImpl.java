package com.future.module.system.service.impl;

import com.future.framework.common.constant.enums.CommonStatus;
import com.future.framework.common.domain.PageResult;
import com.future.framework.common.exception.ServiceException;
import com.future.framework.common.utils.CollUtils;
import com.future.module.system.dao.DictDataMapper;
import com.future.module.system.domain.convert.DictDataConvert;
import com.future.module.system.domain.entity.DictData;
import com.future.module.system.domain.entity.DictType;
import com.future.module.system.domain.query.dict.DictDataCreateQuery;
import com.future.module.system.domain.query.dict.DictDataExportQuery;
import com.future.module.system.domain.query.dict.DictDataPageQuery;
import com.future.module.system.domain.query.dict.DictDataUpdateQuery;
import com.future.module.system.service.DictDataService;
import com.future.module.system.service.DictTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.future.module.system.constants.enums.SystemErrorCode.*;

@Service
public class DictDataServiceImpl implements DictDataService {

    /**
     * 排序 dictType > sort
     */
    private static final Comparator<DictData> COMPARATOR_TYPE_AND_SORT = Comparator
        .comparing(DictData::getDictType)
        .thenComparingInt(DictData::getSort);

    @Resource
    private DictTypeService dictTypeService;

    @Resource
    private DictDataMapper dictDataMapper;

    @Override
    public Long createDictData(DictDataCreateQuery query) {
        // 校验正确性
        checkCreateOrUpdate(null, query.getValue(), query.getDictType());

        // 插入字典类型
        DictData dictData = DictDataConvert.INSTANCE.convert(query);
        dictDataMapper.insert(dictData);
        return dictData.getId();
    }

    @Override
    public void updateDictData(DictDataUpdateQuery query) {
        // 校验正确性
        checkCreateOrUpdate(query.getId(), query.getValue(), query.getDictType());

        // 更新字典类型
        DictData updateObj = DictDataConvert.INSTANCE.convert(query);
        dictDataMapper.updateById(updateObj);
    }

    @Override
    public void deleteDictData(Long id) {
        // 校验是否存在
        checkDictDataExists(id);
        // 删除字典数据
        dictDataMapper.deleteById(id);
    }

    @Override
    public List<DictData> getDictDatas() {
        List<DictData> list = dictDataMapper.selectList();
        list.sort(COMPARATOR_TYPE_AND_SORT);
        return list;
    }

    @Override
    public PageResult<DictData> getDictDataPage(DictDataPageQuery query) {
        return dictDataMapper.selectPage(query);
    }

    @Override
    public List<DictData> getDictDatas(DictDataExportQuery query) {
        List<DictData> list = dictDataMapper.selectList(query);
        list.sort(COMPARATOR_TYPE_AND_SORT);
        return list;
    }

    @Override
    public DictData getDictData(Long id) {
        return dictDataMapper.selectById(id);
    }

    @Override
    public long countByDictType(String dictType) {
        return dictDataMapper.selectCountByDictType(dictType);
    }

    @Override
    public void validDictDatas(String dictType, Collection<String> values) {
        if (CollUtils.isEmpty(values)) {
            return;
        }
        List<DictData> list = dictDataMapper.selectByDictTypeAndValues(dictType, values);
        Map<String, DictData> dictDataMap =
            list.stream().collect(Collectors.toMap(DictData::getValue, i -> i));
        // 校验
        values.forEach(value -> {
            DictData dictData = dictDataMap.get(value);
            if (dictData == null) {
                throw new ServiceException(DICT_DATA_NOT_EXISTS);
            }
            if (!CommonStatus.INVALID.getValue().equals(dictData.getStatus())) {
                throw new ServiceException(DICT_DATA_NOT_ENABLE);
            }
        });
    }

    @Override
    public DictData getDictData(String dictType, String value) {
        return dictDataMapper.selectByDictTypeAndValue(dictType, value);
    }

    @Override
    public DictData parseDictData(String dictType, String label) {
        return dictDataMapper.selectByDictTypeAndLabel(dictType, label);
    }

    private void checkCreateOrUpdate(Long id, String value, String dictType) {
        // 校验自己存在
        checkDictDataExists(id);
        // 校验字典类型有效
        checkDictTypeValid(dictType);
        // 校验字典数据的值的唯一性
        checkDictDataValueUnique(id, dictType, value);
    }

    public void checkDictDataValueUnique(Long id, String dictType, String value) {
        DictData dictData = dictDataMapper.selectByDictTypeAndValue(dictType, value);
        if (dictData == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典数据
        if (id == null) {
            throw new ServiceException(DICT_DATA_VALUE_DUPLICATE);
        }
        if (!dictData.getId().equals(id)) {
            throw new ServiceException(DICT_DATA_VALUE_DUPLICATE);
        }
    }

    public void checkDictDataExists(Long id) {
        if (id == null) {
            return;
        }
        DictData dictData = dictDataMapper.selectById(id);
        if (dictData == null) {
            throw new ServiceException(DICT_DATA_NOT_EXISTS);
        }
    }

    public void checkDictTypeValid(String type) {
        DictType dictType = dictTypeService.getDictType(type);
        if (dictType == null) {
            throw new ServiceException(DICT_TYPE_NOT_EXISTS);
        }
        if (!CommonStatus.INVALID.getValue().equals(dictType.getStatus())) {
            throw new ServiceException(DICT_TYPE_NOT_ENABLE);
        }
    }

}
