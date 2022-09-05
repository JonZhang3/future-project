package com.future.system.domain.query;

import com.future.common.constant.enums.UserState;
import com.future.common.core.domain.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserQuery extends BaseQuery {
    
    private Long id;
    private String username;
    private String phone;
    private UserState userState;
    private Long deptId;
    
}
