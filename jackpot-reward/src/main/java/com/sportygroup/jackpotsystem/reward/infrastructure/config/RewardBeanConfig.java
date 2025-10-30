package com.sportygroup.jackpotsystem.reward.infrastructure.config;

import com.sportygroup.jackpotsystem.reward.domain.RewardStore;
import com.sportygroup.jackpotsystem.reward.infrastructure.store.RewardStoreDatabase;
import com.sportygroup.jackpotsystem.reward.infrastructure.store.mapper.RewardStoreMapper;
import com.sportygroup.jackpotsystem.reward.infrastructure.store.repository.RewardRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RewardBeanConfig {

    @Bean
    RewardStore rewardStore(RewardRepository rewardRepository,
                            RewardStoreMapper rewardStoreMapper) {
        return new RewardStoreDatabase(rewardRepository, rewardStoreMapper);
    }
}


