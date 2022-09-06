package com.future.common.core.domain;

import com.future.common.utils.StringUtils;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

@Data
public class BaseQuery {

    private static final String SORT_TYPE_DESC = "desc";
    private static final String SORT_TYPE_DESCENDING = "descending";
    private static final String SORT_TYPE_ASC = "asc";

    private Integer pageNum;

    private Integer pageSize;

    private String orderBy;

    private String sortType;

    private LocalDateTime createTime;
    
    private LocalDateTime beginTime;
    
    private LocalDateTime endTime;
    
    private String createBy;
    
    public void setSortType(String sortType) {
        if (StringUtils.equalsAny(sortType, SORT_TYPE_DESC, SORT_TYPE_DESCENDING)) {
            this.sortType = SORT_TYPE_DESC;
        } else {
            this.sortType = SORT_TYPE_ASC;
        }
    }

    public Sort getSort() {
        if (StringUtils.isEmpty(orderBy)) {
            return null;
        }
        Sort sort = Sort.by(orderBy);
        if (StringUtils.isEmpty(sortType) || SORT_TYPE_ASC.equals(sortType)) {
            sort = sort.ascending();
        } else {
            sort = sort.descending();
        }
        return sort;
    }

}
