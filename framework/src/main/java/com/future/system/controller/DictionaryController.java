package com.future.system.controller;

import com.future.common.annotation.Log;
import com.future.common.constant.enums.BusinessType;
import com.future.common.core.controller.BaseController;
import com.future.common.core.domain.R;
import com.future.common.core.domain.entity.Dictionary;
import com.future.system.domain.query.DictionaryQuery;
import com.future.system.service.DictionaryService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/system/dict")
public class DictionaryController extends BaseController {

    @Resource(type = DictionaryService.class)
    private DictionaryService dictionaryService;

    @PreAuthorize("@ss.hasPermi('system:dict:list')")
    @GetMapping("/list")
    public R pageList(DictionaryQuery dict) {
        Page<Dictionary> pageData = dictionaryService.pageListDictionaries(dict);
        return R.ok(pageData);
    }

    @Log(title = "字典", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:dict:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, DictionaryQuery query) {

    }

    /**
     * 查询字典类型详细
     */
    @PreAuthorize("@ss.hasPermi('system:dict:query')")
    @GetMapping("/{id}")
    public R getById(@PathVariable("id") Long id) {
        return R.ok(dictionaryService.getDictionaryById(id));
    }

    /**
     * 新增字典
     */
    @Log(title = "字典", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('system:dict:add')")
    @PostMapping
    public R addDictionary(Dictionary dict) {
        if (!dictionaryService.checkDictionaryCodeUnique(dict)) {
            return R.fail("字典已存在");
        }
        dict.setCreateBy(getCurrentUser().getUserId() + "");
        dictionaryService.addDictionary(dict);
        return R.ok();
    }

    /**
     * 更新字典
     */
    @PreAuthorize("@ss.hasPermi('system:dict:edit')")
    @Log(title = "字典", businessType = BusinessType.UPDATE)
    @PutMapping
    public R updateDictionary(Dictionary dict) {
        if (!dictionaryService.checkDictionaryCodeUnique(dict)) {
            return R.fail("字典已存在");
        }
        dict.setUpdateBy(getCurrentUser().getUserId() + "");
        dictionaryService.updateDictionary(dict);
        return R.ok();
    }

    /**
     * 批量删除字典
     */
    @PreAuthorize("@ss.hasPermi('system:dict:remove')")
    @Log(title = "字典", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R deleteDictionaries(@PathVariable List<Long> ids) {
        dictionaryService.deleteByIds(ids);
        return R.ok();
    }

    /**
     * 刷新缓存
     */
    public R refreshCache() {
        return R.ok();
    }

}
