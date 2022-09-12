package com.future.framework.mybatis.domain;

import java.io.Serializable;

/**
 * 排序字段 DTO
 * 
 * @author JonZhang
 */
public class Sort implements Serializable {

    /**
     * 升序
     */
    public static final String ORDER_ASC = "asc";
    /**
     * 降序
     */
    public static final String ORDER_DESC = "desc";

    private String field;
    private String order;

    public Sort() {

    }

    public Sort(String field, String order) {
        this.field = field;
        this.order = order;
    }

    public String getField() {
        return field;
    }

    public Sort setField(String field) {
        this.field = field;
        return this;
    }

    public String getOrder() {
        return order;
    }

    public Sort setOrder(String order) {
        this.order = order;
        return this;
    }

}
