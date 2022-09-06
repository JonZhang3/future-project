package com.future.system.service.impl;

import com.future.common.constant.enums.UserState;
import com.future.common.core.domain.entity.QUser;
import com.future.common.core.domain.entity.User;
import com.future.common.core.service.BaseService;
import com.future.common.utils.StringUtils;
import com.future.system.dao.UserRepo;
import com.future.system.domain.query.UserQuery;
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
    public Page<User> pageListUsers(UserQuery query) {
        QUser quser = QUser.user;
        BooleanBuilder queryBuilder = new BooleanBuilder();
        queryBuilder.and(quser.state.eq(query.getUserState() == null ? UserState.VALID : query.getUserState()));
        if (StringUtils.isNotEmpty(query.getUsername())) {
            queryBuilder.and(quser.username.eq(query.getUsername()));
        }
        if (StringUtils.isNotEmpty(query.getPhone())) {
            queryBuilder.and(quser.phone.eq(query.getPhone()));
        }
        if (query.getBeginTime() != null) {
            queryBuilder.and(quser.createTime.goe(query.getBeginTime()));
        }
        if (query.getEndTime() != null) {
            queryBuilder.and(quser.createTime.loe(query.getEndTime()));
        }
        if (query.getDeptId() != null) {
            queryBuilder.and(quser.deptId.eq(query.getDeptId()));
        }
        return userRepo.findAll(queryBuilder, createPageable(query.getPageNum(), query.getPageSize()));
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
