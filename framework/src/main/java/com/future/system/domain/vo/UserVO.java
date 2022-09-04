package com.future.system.domain.vo;

import com.future.common.core.domain.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserVO extends User {

    private static final long serialVersionUID = 1L;
    
}
