package com.future.framework.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 动态数据源
 * 
 * @author JonZhang
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    
    public DynamicDataSource(DataSource defaultDataSource, Map<Object, Object> dataSources) {
        super.setDefaultTargetDataSource(defaultDataSource);
        super.setTargetDataSources(dataSources);
        super.afterPropertiesSet();
    }
    
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceType();
    }
}
