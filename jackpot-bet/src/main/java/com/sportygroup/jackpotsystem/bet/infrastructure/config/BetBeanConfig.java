package com.sportygroup.jackpotsystem.bet.infrastructure.config;

import com.sportygroup.jackpotsystem.bet.domain.BetStore;
import com.sportygroup.jackpotsystem.bet.domain.GetBetQuery;
import com.sportygroup.jackpotsystem.bet.domain.PlaceBetCommand;
import com.sportygroup.jackpotsystem.bet.infrastructure.messaging.BetEventPublisher;
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

    @Bean
    PlaceBetCommand placeBetCommand(BetStore betStore, BetEventPublisher betEventPublisher) {
        return new PlaceBetCommand(betStore, betEventPublisher);
    }

    @Bean
    GetBetQuery getBetQuery(BetStore betStore) {
        return new GetBetQuery(betStore);
    }
}


