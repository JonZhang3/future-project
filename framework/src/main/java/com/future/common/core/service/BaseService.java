package com.future.common.core.service;

import org.springframework.data.domain.PageRequest;

public interface BaseService {

    default PageRequest createPageable(Integer page, Integer pageSize) {
        if (page == null || page <= 0) {
            page = 1;
        }
        return PageRequest.of(page, pageSize);
    }

}
