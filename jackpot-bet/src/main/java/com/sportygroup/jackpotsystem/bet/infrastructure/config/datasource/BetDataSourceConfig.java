package com.sportygroup.jackpotsystem.bet.infrastructure.config.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import static com.sportygroup.jackpotsystem.bet.infrastructure.config.datasource.DatasourceNaming.BET_DATA_SOURCE;

@Configuration
class BetDataSourceConfig {

    @Bean(name = BET_DATA_SOURCE)
    @ConfigurationProperties(prefix = "jackpot.datasource.bet")
    DataSource betDataSource() {
        return DataSourceBuilder.create().build();
    }
}


