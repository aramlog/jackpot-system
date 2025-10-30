package com.sportygroup.jackpotsystem.reward.infrastructure.config.datasource;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import static com.sportygroup.jackpotsystem.reward.infrastructure.config.datasource.DatasourceNaming.REWARD_DATA_SOURCE;
import static com.sportygroup.jackpotsystem.reward.infrastructure.config.datasource.DatasourceNaming.REWARD_LIQUIBASE;

@Configuration
class RewardLiquibaseConfig {

    @Bean(name = REWARD_LIQUIBASE)
    @ConfigurationProperties(prefix = "jackpot.datasource.reward.liquibase")
    SpringLiquibase rewardLiquibase(@Qualifier(REWARD_DATA_SOURCE) DataSource dataSource) {
        final SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        return liquibase;
    }
}


