package com.future.system.domain.query;

import com.future.common.core.domain.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LoginInfoQuery extends BaseQuery {
    
    private String ip;
    
    private Integer status;
    
    private String username;
    
}
