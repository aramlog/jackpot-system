package com.sportygroup.jackpotsystem.contribution.infrastructure.config.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import static com.sportygroup.jackpotsystem.contribution.infrastructure.config.datasource.DatasourceNaming.CONTRIBUTION_DATA_SOURCE;

@Configuration
class ContributionDataSourceConfig {

    @Bean(name = CONTRIBUTION_DATA_SOURCE)
    @ConfigurationProperties(prefix = "jackpot.datasource.contribution")
    DataSource contributionDataSource() {
        return DataSourceBuilder.create().build();
    }
}


