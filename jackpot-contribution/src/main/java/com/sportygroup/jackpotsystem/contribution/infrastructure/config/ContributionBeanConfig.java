package com.sportygroup.jackpotsystem.contribution.infrastructure.config;

import com.sportygroup.jackpotsystem.contribution.domain.contribution.ContributionStore;
import com.sportygroup.jackpotsystem.contribution.domain.strategy.ContributionStrategyFactory;
import com.sportygroup.jackpotsystem.contribution.domain.jackpot.CreateJackpotCommand;
import com.sportygroup.jackpotsystem.contribution.domain.contribution.DeleteContributionsCommand;
import com.sportygroup.jackpotsystem.contribution.domain.contribution.GetContributionQuery;
import com.sportygroup.jackpotsystem.contribution.domain.contribution.GetContributionsQuery;
import com.sportygroup.jackpotsystem.contribution.domain.jackpot.JackpotStore;
import com.sportygroup.jackpotsystem.contribution.domain.ProcessBetEventCommand;
import com.sportygroup.jackpotsystem.contribution.infrastructure.store.ContributionStoreDatabase;
import com.sportygroup.jackpotsystem.contribution.infrastructure.store.JackpotStoreDatabase;
import com.sportygroup.jackpotsystem.contribution.infrastructure.store.mapper.ContributionStoreMapper;
import com.sportygroup.jackpotsystem.contribution.infrastructure.store.mapper.JackpotStoreMapper;
import com.sportygroup.jackpotsystem.contribution.infrastructure.store.repository.ContributionRepository;
import com.sportygroup.jackpotsystem.contribution.infrastructure.store.repository.JackpotRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContributionBeanConfig {

    @Bean
    ContributionStore contributionStore(ContributionRepository contributionRepository,
                                        ContributionStoreMapper contributionStoreMapper) {
        return new ContributionStoreDatabase(contributionRepository, contributionStoreMapper);
    }

    @Bean
    JackpotStore jackpotStore(JackpotRepository jackpotRepository,
                               JackpotStoreMapper jackpotStoreMapper) {
        return new JackpotStoreDatabase(jackpotRepository, jackpotStoreMapper);
    }

    @Bean
    ContributionStrategyFactory contributionStrategyFactory() {
        return new ContributionStrategyFactory();
    }

    @Bean
    ProcessBetEventCommand processBetEventCommand(ContributionStore contributionStore,
                                                   JackpotStore jackpotStore,
                                                   ContributionStrategyFactory contributionStrategyFactory) {
        return new ProcessBetEventCommand(contributionStore, jackpotStore, contributionStrategyFactory);
    }

    @Bean
    GetContributionsQuery getContributionsQuery(ContributionStore contributionStore) {
        return new GetContributionsQuery(contributionStore);
    }

    @Bean
    CreateJackpotCommand createJackpotCommand(JackpotStore jackpotStore) {
        return new CreateJackpotCommand(jackpotStore);
    }

    @Bean
    DeleteContributionsCommand deleteContributionsCommand(ContributionStore contributionStore) {
        return new DeleteContributionsCommand(contributionStore);
    }

    @Bean
    GetContributionQuery getContributionQuery(ContributionStore contributionStore) {
        return new GetContributionQuery(contributionStore);
    }
}


