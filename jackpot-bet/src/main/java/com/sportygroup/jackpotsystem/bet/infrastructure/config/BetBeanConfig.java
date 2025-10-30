package com.sportygroup.jackpotsystem.bet.infrastructure.config;

import com.sportygroup.jackpotsystem.bet.domain.BetStore;
import com.sportygroup.jackpotsystem.bet.infrastructure.store.BetStoreDatabase;
import com.sportygroup.jackpotsystem.bet.infrastructure.store.mapper.BetStoreMapper;
import com.sportygroup.jackpotsystem.bet.infrastructure.store.repository.BetRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BetBeanConfig {

    @Bean
    BetStore betStore(BetRepository betRepository, BetStoreMapper betStoreMapper) {
        return new BetStoreDatabase(betRepository, betStoreMapper);
    }
}


