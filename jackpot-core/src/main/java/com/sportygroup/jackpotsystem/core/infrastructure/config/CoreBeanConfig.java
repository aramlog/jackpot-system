package com.sportygroup.jackpotsystem.core.infrastructure.config;

import com.sportygroup.jackpotsystem.core.domain.store.ContributionStore;
import com.sportygroup.jackpotsystem.core.infrastructure.store.ContributionStoreRest;
import com.sportygroup.jackpotsystem.core.infrastructure.store.client.ContributionFeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreBeanConfig {

    @Bean
    ContributionStore coreContributionStore(ContributionFeignClient contributionFeignClient) {
        return new ContributionStoreRest(contributionFeignClient);
    }
}

