package com.sportygroup.jackpotsystem.reward.infrastructure.config.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import static com.sportygroup.jackpotsystem.reward.infrastructure.config.datasource.DatasourceNaming.REWARD_DATA_SOURCE;

@Configuration
class RewardDataSourceConfig {

    @Bean(name = REWARD_DATA_SOURCE)
    @ConfigurationProperties(prefix = "jackpot.datasource.reward")
    DataSource rewardDataSource() {
        return DataSourceBuilder.create().build();
    }
}