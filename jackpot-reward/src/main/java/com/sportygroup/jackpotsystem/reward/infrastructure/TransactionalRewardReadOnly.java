package com.sportygroup.jackpotsystem.reward.infrastructure;

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.sportygroup.jackpotsystem.reward.infrastructure.config.datasource.DatasourceNaming.REWARD_TRANSACTION_MANAGER;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Transactional(transactionManager = REWARD_TRANSACTION_MANAGER, readOnly = true)
public @interface TransactionalRewardReadOnly {
}


