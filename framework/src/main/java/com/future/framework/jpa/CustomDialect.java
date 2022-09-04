package com.future.framework.jpa;

import org.hibernate.dialect.MySQL8Dialect;

/**
 * 自定义数据库的方言
 * 
 * @author JonZhang
 */
public class CustomDialect extends MySQL8Dialect {

    /**
     * 定义表类型，在生成表的时候会使用到
     */
    @Override
    public String getTableTypeString() {
        return " ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC ";
    }
    
}
