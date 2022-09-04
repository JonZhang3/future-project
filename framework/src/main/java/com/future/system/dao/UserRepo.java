package com.future.system.dao;

import com.future.common.constant.enums.UserState;
import com.future.common.core.domain.entity.User;
import com.future.framework.jpa.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends BaseJpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    @Query("update User set state=?1 where id in (?2)")
    void updateStateByIds(UserState state, List<Long> ids);

}
