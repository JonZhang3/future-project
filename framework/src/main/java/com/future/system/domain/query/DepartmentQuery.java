package com.future.system.domain.query;

import com.future.common.constant.enums.State;
import com.future.common.core.domain.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DepartmentQuery extends BaseQuery {
    
    private Long id;// 部门ID
    
    private Long parentId;// 父ID
    
    private String deptName;// 部门名称
    
    private State state;// 状态
    
}
