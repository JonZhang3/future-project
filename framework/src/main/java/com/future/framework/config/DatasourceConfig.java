package com.future.framework.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceWrapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 数据源配置
 * 
 * @author JonZhang
 */
@Configuration
public class DatasourceConfig {
    
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.master", name = "enabled", havingValue = "true")
    public DataSource masterDataSource(DruidDataSourceWrapper properties) {
        return null;
    }
    
}
