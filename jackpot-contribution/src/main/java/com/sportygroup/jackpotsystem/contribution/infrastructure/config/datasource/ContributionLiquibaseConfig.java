package com.sportygroup.jackpotsystem.contribution.infrastructure.config.datasource;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import static com.sportygroup.jackpotsystem.contribution.infrastructure.config.datasource.DatasourceNaming.CONTRIBUTION_DATA_SOURCE;
import static com.sportygroup.jackpotsystem.contribution.infrastructure.config.datasource.DatasourceNaming.CONTRIBUTION_LIQUIBASE;

@Configuration
class ContributionLiquibaseConfig {

    @Bean(name = CONTRIBUTION_LIQUIBASE)
    @ConfigurationProperties(prefix = "jackpot.datasource.contribution.liquibase")
    SpringLiquibase contributionLiquibase(@Qualifier(CONTRIBUTION_DATA_SOURCE) DataSource dataSource) {
        final SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        return liquibase;
    }
}


