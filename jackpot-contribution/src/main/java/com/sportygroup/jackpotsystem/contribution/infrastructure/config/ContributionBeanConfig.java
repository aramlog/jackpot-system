package com.sportygroup.jackpotsystem.contribution.infrastructure.config;

import com.sportygroup.jackpotsystem.contribution.domain.ContributionStore;
import com.sportygroup.jackpotsystem.contribution.domain.ProcessBetEventCommand;
import com.sportygroup.jackpotsystem.contribution.infrastructure.store.ContributionStoreDatabase;
import com.sportygroup.jackpotsystem.contribution.infrastructure.store.mapper.ContributionStoreMapper;
import com.sportygroup.jackpotsystem.contribution.infrastructure.store.repository.ContributionRepository;
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
    ProcessBetEventCommand processBetEventCommand(ContributionStore contributionStore) {
        return new ProcessBetEventCommand(contributionStore);
    }
}


