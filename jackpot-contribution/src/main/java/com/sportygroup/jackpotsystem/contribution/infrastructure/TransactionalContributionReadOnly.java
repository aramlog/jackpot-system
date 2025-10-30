package com.sportygroup.jackpotsystem.contribution.infrastructure;

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.sportygroup.jackpotsystem.contribution.infrastructure.config.datasource.DatasourceNaming.CONTRIBUTION_TRANSACTION_MANAGER;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Transactional(transactionManager = CONTRIBUTION_TRANSACTION_MANAGER, readOnly = true)
public @interface TransactionalContributionReadOnly {
}


