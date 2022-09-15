package com.future.module.system.domain.convert;

import com.future.module.system.domain.entity.AdminUser;
import com.future.module.system.domain.entity.Department;
import com.future.module.system.domain.entity.Post;
import com.future.module.system.domain.entity.Role;
import com.future.module.system.domain.vo.user.UserExcelVO;
import com.future.module.system.domain.vo.user.UserPageItemRespVO;
import com.future.module.system.domain.vo.user.UserProfileRespVO;
import com.future.module.system.domain.vo.user.UserSimpleRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserConvert {
    
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserProfileRespVO convertToUserProfile(AdminUser bean);

    List<UserProfileRespVO.Role> convertToRoleList(List<Role> list);

    UserProfileRespVO.Dept convertToDept(Department bean);

    List<UserProfileRespVO.Post> convertToPostList(List<Post> list);

    UserPageItemRespVO convertToUserPageItem(AdminUser bean);

    UserPageItemRespVO.Dept convertToUserPageItemDept(Department bean);

    List<UserSimpleRespVO> convertToSimpleUserList(List<AdminUser> list);

    UserExcelVO convertToUserExcel(AdminUser bean);
    
}
