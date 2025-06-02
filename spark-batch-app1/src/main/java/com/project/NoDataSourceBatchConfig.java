package com.project;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NoDataSourceBatchConfig extends DefaultBatchConfigurer {
    // Override to disable default DataSource requirement
    @Override
    public void setDataSource(javax.sql.DataSource dataSource) {
        // Do nothing to disable database usage
    }
}
