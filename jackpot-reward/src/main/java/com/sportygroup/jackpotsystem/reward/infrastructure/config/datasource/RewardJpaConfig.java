package com.sportygroup.jackpotsystem.reward.infrastructure.config.datasource;

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

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = DatasourceNaming.REWARD_ENTITY_MANAGER_FACTORY,
        transactionManagerRef = DatasourceNaming.REWARD_TRANSACTION_MANAGER,
        basePackages = {DatasourceNaming.REWARD_BASE_PACKAGE}
)
@EnableTransactionManagement
public class RewardJpaConfig {

    @Bean(name = DatasourceNaming.REWARD_ENTITY_MANAGER_FACTORY)
    LocalContainerEntityManagerFactoryBean rewardEntityManagerFactory(
            @Qualifier(DatasourceNaming.REWARD_DATA_SOURCE) DataSource dataSource,
            JpaProperties jpaProperties) {
        final var vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(jpaProperties.isGenerateDdl());
        vendorAdapter.setShowSql(jpaProperties.isShowSql());
        final var properties = new java.util.HashMap<String, Object>(jpaProperties.getProperties());
        final var factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan(DatasourceNaming.REWARD_BASE_PACKAGE);
        factoryBean.setPersistenceUnitName("reward");
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setJpaPropertyMap(properties);
        return factoryBean;
    }

    @Bean(name = DatasourceNaming.REWARD_TRANSACTION_MANAGER)
    PlatformTransactionManager rewardTransactionManager(
            @Qualifier(DatasourceNaming.REWARD_ENTITY_MANAGER_FACTORY) EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}


