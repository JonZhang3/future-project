package com.future.system.domain.dto;

import com.future.common.constant.enums.UserState;
import com.future.common.core.domain.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseDTO {
    
    private Long id;
    private String username;
    private String phone;
    private UserState userState;
    private Long deptId;
    
}
