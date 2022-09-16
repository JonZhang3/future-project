package com.future.module.system.domain.convert;

import com.future.module.system.domain.entity.Department;
import com.future.module.system.domain.entity.Post;
import com.future.module.system.domain.entity.Role;
import com.future.module.system.domain.entity.User;
import com.future.module.system.domain.query.user.UserCreateQuery;
import com.future.module.system.domain.query.user.UserImportExcelQuery;
import com.future.module.system.domain.query.user.UserProfileUpdateQuery;
import com.future.module.system.domain.query.user.UserUpdateQuery;
import com.future.module.system.domain.vo.user.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import javax.security.auth.callback.PasswordCallback;
import java.util.List;

@Mapper
public interface UserConvert {
    
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserProfileRespVO convertToUserProfile(User bean);

    UserProfileRespVO.Role convertToRole(Role role);
    
    List<UserProfileRespVO.Role> convertToRoleList(List<Role> list);

    UserProfileRespVO.Dept convertToDept(Department bean);

    UserProfileRespVO.Post convertToPost(Post post);
    
    List<UserProfileRespVO.Post> convertToPostList(List<Post> list);

    UserPageItemRespVO convertToUserPageItem(User bean);
    
    UserPageItemRespVO.Dept convertToUserPageItemDept(Department bean);

    UserSimpleRespVO convertToSimpleUser(User bean);
    
    List<UserSimpleRespVO> convertToSimpleUserList(List<User> list);

    UserExcelVO convertToUserExcel(User bean);

    User convert(UserCreateQuery bean);

    User convert(UserUpdateQuery bean);

    User convert(UserProfileUpdateQuery bean);

    User convert(UserImportExcelQuery bean);
}
