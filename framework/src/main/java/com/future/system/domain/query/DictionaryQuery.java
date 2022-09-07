package com.future.system.domain.query;

import com.future.common.constant.enums.State;
import com.future.common.core.domain.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DictionaryQuery extends BaseQuery {

    private String name;
    private State state;
    private String code;

}
