package com.sportygroup.jackpotsystem.reward.infrastructure.config;

import com.sportygroup.jackpotsystem.reward.domain.strategy.FixedRewardStrategy;
import com.sportygroup.jackpotsystem.reward.domain.strategy.RewardStrategy;
import com.sportygroup.jackpotsystem.reward.domain.strategy.VariableRewardStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class RewardStrategyConfig {

    private final RewardProperties properties;

    @Bean
    public RewardStrategy rewardStrategy() {
        return switch (properties.getType()) {
            case FIXED -> new FixedRewardStrategy(properties.getFixed().getWinChance());
            case VARIABLE -> new VariableRewardStrategy(
                    properties.getVariable().getInitialChance(),
                    properties.getVariable().getMaxChance(),
                    properties.getVariable().getThreshold()
            );
        };
    }
}

