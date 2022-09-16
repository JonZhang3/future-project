package com.future.module.admin.controller;

import com.future.framework.common.constant.enums.CommonStatus;
import com.future.framework.common.domain.R;
import com.future.module.system.domain.convert.MenuConvert;
import com.future.module.system.domain.entity.Menu;
import com.future.module.system.domain.query.permission.MenuCreateQuery;
import com.future.module.system.domain.query.permission.MenuListQuery;
import com.future.module.system.domain.query.permission.MenuUpdateQuery;
import com.future.module.system.domain.vo.permission.MenuRespVO;
import com.future.module.system.domain.vo.permission.MenuSimpleRespVO;
import com.future.module.system.service.MenuService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/admin-api/system/menu")
@Validated
public class MenuController {

    @Resource
    private MenuService menuService;

    @PostMapping("/create")
    @PreAuthorize("@ss.hasPermission('system:menu:create')")
    public R<Long> createMenu(@Valid @RequestBody MenuCreateQuery query) {
        Long menuId = menuService.createMenu(query);
        return R.ok(menuId);
    }

    @PutMapping("/update")
    @PreAuthorize("@ss.hasPermission('system:menu:update')")
    public R<Boolean> updateMenu(@Valid @RequestBody MenuUpdateQuery query) {
        menuService.updateMenu(query);
        return R.ok(true);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("@ss.hasPermission('system:menu:delete')")
    public R<Boolean> deleteMenu(@RequestParam("id") Long id) {
        menuService.deleteMenu(id);
        return R.ok(true);
    }

    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermission('system:menu:query')")
    public R<List<MenuRespVO>> getMenus(MenuListQuery query) {
        List<Menu> list = menuService.getMenus(query);
        list.sort(Comparator.comparing(Menu::getSort));
        return R.ok(MenuConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/list-all-simple")
    public R<List<MenuSimpleRespVO>> getSimpleMenus() {
        // 获得菜单列表，只要开启状态的
        MenuListQuery reqVO = new MenuListQuery();
        reqVO.setStatus(CommonStatus.VALID.getValue());
        List<Menu> list = menuService.getMenus(reqVO);
        // 排序后，返回给前端
        list.sort(Comparator.comparing(Menu::getSort));
        return R.ok(MenuConvert.INSTANCE.convertToSimpleList(list));
    }

    @GetMapping("/get")
    @PreAuthorize("@ss.hasPermission('system:menu:query')")
    public R<MenuRespVO> getMenu(Long id) {
        Menu menu = menuService.getMenu(id);
        return R.ok(MenuConvert.INSTANCE.convert(menu));
    }

}
