package com.mytech.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthContributorAutoConfiguration;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-03-27
 * @description : this is to solve the shardingSphere exception:DataSource health check failed.
 * Actually it is a spring-boot-actuator bug, the early version spring-boot-actuator 2.2.7.RELEASE is good,
 * This project is using 2.4.3
 */
@Configuration
public class DataSourceHealthConfig extends DataSourceHealthContributorAutoConfiguration {
    //this is from version2.2.7
    private static final String DEFAULT_QUERY = "SELECT 1";

    public DataSourceHealthConfig(Map<String, DataSource> dataSources, ObjectProvider<DataSourcePoolMetadataProvider> metadataProviders) {
        super(dataSources, metadataProviders);
    }

    @Override
    protected AbstractHealthIndicator createIndicator(DataSource source) {
        DataSourceHealthIndicator indicator = (DataSourceHealthIndicator) super.createIndicator(source);
        if (!StringUtils.hasText(indicator.getQuery())){
            indicator.setQuery(DEFAULT_QUERY);
        }
        return indicator;
    }
}
