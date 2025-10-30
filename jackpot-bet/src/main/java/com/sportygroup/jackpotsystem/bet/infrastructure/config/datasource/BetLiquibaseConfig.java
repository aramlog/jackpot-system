package com.sportygroup.jackpotsystem.bet.infrastructure.config.datasource;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import static com.sportygroup.jackpotsystem.bet.infrastructure.config.datasource.DatasourceNaming.BET_DATA_SOURCE;
import static com.sportygroup.jackpotsystem.bet.infrastructure.config.datasource.DatasourceNaming.BET_LIQUIBASE;

@Configuration
class BetLiquibaseConfig {

    @Bean(name = BET_LIQUIBASE)
    @ConfigurationProperties(prefix = "jackpot.datasource.bet.liquibase")
    SpringLiquibase betLiquibase(@Qualifier(BET_DATA_SOURCE) DataSource dataSource) {
        final SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        return liquibase;
    }
}


