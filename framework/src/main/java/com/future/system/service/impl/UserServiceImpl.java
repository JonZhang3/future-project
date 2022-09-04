package com.future.system.service.impl;

import com.future.common.constant.enums.UserState;
import com.future.common.core.domain.entity.QUser;
import com.future.common.core.domain.entity.User;
import com.future.common.core.service.BaseService;
import com.future.common.utils.StringUtils;
import com.future.system.dao.UserRepo;
import com.future.system.domain.dto.UserDTO;
import com.future.system.service.UserService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements BaseService, UserService {

    @Resource(type = JPAQueryFactory.class)
    private JPAQueryFactory queryFactory;

    @Resource(type = UserRepo.class)
    private UserRepo userRepo;

    @Override
    public Page<User> pageFindUsers(UserDTO dto) {
        QUser quser = QUser.user;
        BooleanBuilder queryBuilder = new BooleanBuilder();
        queryBuilder.and(quser.state.eq(dto.getUserState() == null ? UserState.VALID : dto.getUserState()));
        if (StringUtils.isNotEmpty(dto.getUsername())) {
            queryBuilder.and(quser.username.eq(dto.getUsername()));
        }
        if (StringUtils.isNotEmpty(dto.getPhone())) {
            queryBuilder.and(quser.phone.eq(dto.getPhone()));
        }
        if (dto.getBegintTime() != null) {
            queryBuilder.and(quser.createTime.goe(dto.getBegintTime()));
        }
        if (dto.getEndTime() != null) {
            queryBuilder.and(quser.createTime.loe(dto.getEndTime()));
        }
        if (dto.getDeptId() != null) {
            queryBuilder.and(quser.deptId.eq(dto.getDeptId()));
        }
        return userRepo.findAll(queryBuilder, createPageable(dto.getPageNum(), dto.getPageSize()));
    }

    @Override
    public List<User> pageFindAllocatedUsers() {
        return null;
    }

    @Transactional
    @Override
    public void deleteUsersByIds(List<Long> ids) {
        // 删除用户与角色关联
        // 删除用户与岗位关联
        // 删除用户
        userRepo.updateStateByIds(UserState.DELETED, ids);
    }
}
