package com.future.system.service.impl;

import com.future.common.constant.enums.State;
import com.future.common.core.domain.entity.DictionaryItem;
import com.future.system.dao.DictionaryItemRepo;
import com.future.system.service.DictionaryItemService;

import javax.annotation.Resource;
import java.util.List;

public class DictionaryItemServiceImpl implements DictionaryItemService {

    @Resource(type = DictionaryItemRepo.class)
    private DictionaryItemRepo dictionaryItemRepo;

    /**
     * 分页查询字典数据
     */
    public void pageListItems(DictionaryItem item) {

    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     */
    @Override
    public String getItemLabel(String code, String itemVaue) {
        DictionaryItem item = dictionaryItemRepo.findByDictAndValue(code, itemVaue);
        return item != null ? item.getLabel() : null;
    }

    /**
     * 根据字典数据ID查询信息
     */
    @Override
    public DictionaryItem getItemById(Long id) {
        return dictionaryItemRepo.findById(id).orElse(null);
    }

    /**
     * 批量删除字典数据
     */
    @Override
    public void deleteByIds(List<Long> ids) {
        dictionaryItemRepo.deleteAllById(ids);
    }

    /**
     * 新增字典数据信息
     */
    @Override
    public void addItem(DictionaryItem item) {
        item.setState(State.VALID);
        dictionaryItemRepo.saveAndFlush(item);
    }

    /**
     * 更新字典数据
     */
    @Override
    public void updateItem(DictionaryItem item) {
        dictionaryItemRepo.saveAndFlush(item);
    }

}
