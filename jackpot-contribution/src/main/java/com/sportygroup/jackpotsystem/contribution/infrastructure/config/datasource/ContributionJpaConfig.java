package com.sportygroup.jackpotsystem.contribution.infrastructure.config.datasource;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

import static com.sportygroup.jackpotsystem.contribution.infrastructure.config.datasource.DatasourceNaming.*;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = CONTRIBUTION_ENTITY_MANAGER_FACTORY,
        transactionManagerRef = CONTRIBUTION_TRANSACTION_MANAGER,
        basePackages = {CONTRIBUTION_BASE_PACKAGE}
)
@EnableTransactionManagement
public class ContributionJpaConfig {

    @Bean(name = CONTRIBUTION_ENTITY_MANAGER_FACTORY)
    LocalContainerEntityManagerFactoryBean contributionEntityManagerFactory(
            @Qualifier(CONTRIBUTION_DATA_SOURCE) DataSource dataSource,
            JpaProperties jpaProperties) {

        final var vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(jpaProperties.isGenerateDdl());
        vendorAdapter.setShowSql(jpaProperties.isShowSql());

        final var properties = new HashMap<String, Object>(jpaProperties.getProperties());
        final var factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan(CONTRIBUTION_BASE_PACKAGE);
        factoryBean.setPersistenceUnitName("contribution");
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setJpaPropertyMap(properties);

        return factoryBean;
    }

    @Bean(name = CONTRIBUTION_TRANSACTION_MANAGER)
    PlatformTransactionManager contributionTransactionManager(
            @Qualifier(CONTRIBUTION_ENTITY_MANAGER_FACTORY) EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}


