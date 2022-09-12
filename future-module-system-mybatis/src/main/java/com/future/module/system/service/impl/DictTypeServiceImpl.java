package com.future.module.system.service.impl;

import com.future.framework.common.domain.PageResult;
import com.future.framework.common.exception.ServiceException;
import com.future.framework.common.utils.StringUtils;
import com.future.module.system.dao.DictTypeMapper;
import com.future.module.system.domain.convert.DictTypeConvert;
import com.future.module.system.domain.entity.DictType;
import com.future.module.system.domain.query.dict.DictTypeCreateQuery;
import com.future.module.system.domain.query.dict.DictTypeExportQuery;
import com.future.module.system.domain.query.dict.DictTypePageQuery;
import com.future.module.system.domain.query.dict.DictTypeUpdateQuery;
import com.future.module.system.service.DictDataService;
import com.future.module.system.service.DictTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.future.module.system.constants.enums.SystemErrorCode.*;

@Service
public class DictTypeServiceImpl implements DictTypeService {

    @Resource
    private DictTypeMapper dictTypeMapper;

    @Resource
    private DictDataService dictDataService;

    @Override
    public Long createDictType(DictTypeCreateQuery query) {
        // 校验正确性
        checkCreateOrUpdate(null, query.getName(), query.getType());
        // 插入字典类型
        DictType dictType = DictTypeConvert.INSTANCE.convert(query);
        dictTypeMapper.insert(dictType);
        return dictType.getId();
    }

    @Override
    public void updateDictType(DictTypeUpdateQuery query) {
        // 校验正确性
        checkCreateOrUpdate(query.getId(), query.getName(), null);
        // 更新字典类型
        DictType updateObj = DictTypeConvert.INSTANCE.convert(query);
        dictTypeMapper.updateById(updateObj);
    }

    @Override
    public void deleteDictType(Long id) {
        // 校验是否存在
        DictType dictType = checkDictTypeExists(id);
        // 校验是否有字典数据
        if (dictDataService.countByDictType(dictType.getType()) > 0) {
            throw new ServiceException(DICT_TYPE_HAS_CHILDREN);
        }
        // 删除字典类型
        dictTypeMapper.deleteById(id);
    }

    @Override
    public PageResult<DictType> getDictTypePage(DictTypePageQuery query) {
        return dictTypeMapper.selectPage(query);
    }

    @Override
    public List<DictType> getDictTypeList(DictTypeExportQuery query) {
        return dictTypeMapper.selectList(query);
    }

    @Override
    public DictType getDictType(Long id) {
        return dictTypeMapper.selectById(id);
    }

    @Override
    public DictType getDictType(String type) {
        return dictTypeMapper.selectByType(type);
    }

    @Override
    public List<DictType> getDictTypeList() {
        return dictTypeMapper.selectList();
    }

    private void checkCreateOrUpdate(Long id, String name, String type) {
        // 校验自己存在
        checkDictTypeExists(id);
        // 校验字典类型的名字的唯一性
        checkDictTypeNameUnique(id, name);
        // 校验字典类型的类型的唯一性
        checkDictTypeUnique(id, type);
    }

    public void checkDictTypeNameUnique(Long id, String name) {
        DictType dictType = dictTypeMapper.selectByName(name);
        if (dictType == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
        if (id == null) {
            throw new ServiceException(DICT_TYPE_NAME_DUPLICATE);
        }
        if (!dictType.getId().equals(id)) {
            throw new ServiceException(DICT_TYPE_NAME_DUPLICATE);
        }
    }

    public void checkDictTypeUnique(Long id, String type) {
        if (StringUtils.isEmpty(type)) {
            return;
        }
        DictType dictType = dictTypeMapper.selectByType(type);
        if (dictType == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
        if (id == null) {
            throw new ServiceException(DICT_TYPE_TYPE_DUPLICATE);
        }
        if (!dictType.getId().equals(id)) {
            throw new ServiceException(DICT_TYPE_TYPE_DUPLICATE);
        }
    }

    public DictType checkDictTypeExists(Long id) {
        if (id == null) {
            return null;
        }
        DictType dictType = dictTypeMapper.selectById(id);
        if (dictType == null) {
            throw new ServiceException(DICT_TYPE_NOT_EXISTS);
        }
        return dictType;
    }

}
