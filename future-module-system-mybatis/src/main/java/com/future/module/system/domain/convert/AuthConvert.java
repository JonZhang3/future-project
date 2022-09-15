package com.future.module.system.domain.convert;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.LoggerFactory;

import com.future.framework.common.utils.CollUtils;
import com.future.module.system.constants.enums.MenuId;
import com.future.module.system.domain.entity.AdminUser;
import com.future.module.system.domain.entity.Menu;
import com.future.module.system.domain.entity.Role;
import com.future.module.system.domain.vo.auth.AuthMenuVO;
import com.future.module.system.domain.vo.auth.AuthPermissionInfoVO;

@Mapper
public interface AuthConvert {

    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    AuthMenuVO convertTreeNode(Menu menu);

    /**
     * 将菜单列表，构建成菜单树
     *
     * @param menuList 菜单列表
     * @return 菜单树
     */
    default List<AuthMenuVO> buildMenuTree(List<Menu> menuList) {
        // 排序，保证菜单的有序性
        menuList.sort(Comparator.comparing(Menu::getSort));
        // 构建菜单树
        // 使用 LinkedHashMap 的原因，是为了排序 。实际也可以用 Stream API ，就是太丑了。
        Map<Long, AuthMenuVO> treeNodeMap = new LinkedHashMap<>();
        menuList.forEach(menu -> treeNodeMap.put(menu.getId(), AuthConvert.INSTANCE.convertTreeNode(menu)));
        // 处理父子关系
        treeNodeMap.values().stream().filter(node -> !node.getParentId().equals(MenuId.ROOT.getId()))
                .forEach(childNode -> {
                    // 获得父节点
                    AuthMenuVO parentNode = treeNodeMap.get(childNode.getParentId());
                    if (parentNode == null) {
                        LoggerFactory.getLogger(getClass()).error("[buildRouterTree][resource({}) 找不到父资源({})]",
                                childNode.getId(), childNode.getParentId());
                        return;
                    }
                    // 将自己添加到父节点中
                    if (parentNode.getChildren() == null) {
                        parentNode.setChildren(new ArrayList<>());
                    }
                    parentNode.getChildren().add(childNode);
                });
        // 获得到所有的根节点
        return CollUtils.filterList(treeNodeMap.values(), node -> MenuId.ROOT.getId().equals(node.getParentId()));
    }

    default AuthPermissionInfoVO convert(AdminUser user, List<Role> roleList, List<Menu> menuList) {
        return AuthPermissionInfoVO.builder()
                .user(AuthPermissionInfoVO.UserVO.builder().id(user.getId()).nickname(user.getNickname())
                        .avatar(user.getAvatar()).build())
                .roles(CollUtils.convertSet(roleList, Role::getCode))
                .permissions(CollUtils.convertSet(menuList, Menu::getPermission))
                .build();
    }

}
