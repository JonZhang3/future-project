package com.future.module.system.domain.convert;

import com.future.module.system.domain.entity.Menu;
import com.future.module.system.domain.query.permission.MenuCreateQuery;
import com.future.module.system.domain.query.permission.MenuUpdateQuery;
import com.future.module.system.domain.vo.permission.MenuRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MenuConvert {

    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);
    
    Menu convert(MenuCreateQuery bean);

    Menu convert(MenuUpdateQuery bean);

    MenuRespVO convert(Menu bean);

    List<MenuRespVO> convertList(List<Menu> list);
    
}
