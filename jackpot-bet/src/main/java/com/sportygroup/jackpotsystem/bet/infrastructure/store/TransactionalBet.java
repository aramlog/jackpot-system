package com.sportygroup.jackpotsystem.bet.infrastructure.store;

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.sportygroup.jackpotsystem.bet.infrastructure.config.datasource.DatasourceNaming.BET_TRANSACTION_MANAGER;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Transactional(BET_TRANSACTION_MANAGER)
public @interface TransactionalBet {
}
