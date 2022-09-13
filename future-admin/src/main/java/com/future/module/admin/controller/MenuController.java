package com.future.module.admin.controller;

import com.future.framework.common.constant.enums.CommonStatus;
import com.future.framework.common.domain.R;
import com.future.module.system.domain.convert.MenuConvert;
import com.future.module.system.domain.entity.Menu;
import com.future.module.system.domain.query.permission.MenuCreateQuery;
import com.future.module.system.domain.query.permission.MenuListQuery;
import com.future.module.system.domain.query.permission.MenuUpdateQuery;
import com.future.module.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

@Api(tags = "管理后台 - 菜单")
@RestController
@RequestMapping("/system/menu")
@Validated
public class MenuController {

    @Resource
    private MenuService menuService;

    @PostMapping("/create")
    @ApiOperation("创建菜单")
    @PreAuthorize("@ss.hasPermission('system:menu:create')")
    public R createMenu(@Valid @RequestBody MenuCreateQuery query) {
        Long menuId = menuService.createMenu(query);
        return R.ok(menuId);
    }

    @PutMapping("/update")
    @ApiOperation("修改菜单")
    @PreAuthorize("@ss.hasPermission('system:menu:update')")
    public R updateMenu(@Valid @RequestBody MenuUpdateQuery query) {
        menuService.updateMenu(query);
        return R.ok(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除菜单")
    @ApiImplicitParam(name = "id", value = "角色编号", required= true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:menu:delete')")
    public R deleteMenu(@RequestParam("id") Long id) {
        menuService.deleteMenu(id);
        return R.ok(true);
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取菜单列表", notes = "用于【菜单管理】界面")
    @PreAuthorize("@ss.hasPermission('system:menu:query')")
    public R getMenus(MenuListQuery query) {
        List<Menu> list = menuService.getMenus(query);
        list.sort(Comparator.comparing(Menu::getSort));
        return R.ok(MenuConvert.INSTANCE.convertList(list));
    }

    @ApiOperation(value = "获取菜单精简信息列表", notes = "只包含被开启的菜单，用于【角色分配菜单】功能的选项。")
    @GetMapping("/list-all-simple")
    public R getSimpleMenus() {
        // 获得菜单列表，只要开启状态的
        MenuListReqVO reqVO = new MenuListReqVO();
        reqVO.setStatus(CommonStatus.VALID.getValue());
        List<Menu> list = menuService.getTenantMenus(reqVO);
        // 排序后，返回给前端
        list.sort(Comparator.comparing(Menu::getSort));
        return R.ok(MenuConvert.INSTANCE.convertList02(list));
    }

    @GetMapping("/get")
    @ApiOperation("获取菜单信息")
    @PreAuthorize("@ss.hasPermission('system:menu:query')")
    public R getMenu(Long id) {
        Menu menu = menuService.getMenu(id);
        return R.ok(MenuConvert.INSTANCE.convert(menu));
    }
    
}
