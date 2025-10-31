package com.sportygroup.jackpotsystem.contribution.infrastructure.config;

import com.sportygroup.jackpotsystem.contribution.domain.ContributionStrategy;
import com.sportygroup.jackpotsystem.contribution.domain.FixedContributionStrategy;
import com.sportygroup.jackpotsystem.contribution.domain.VariableContributionStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class ContributionStrategyConfig {

    private final ContributionProperties properties;

    @Bean
    public ContributionStrategy contributionStrategy() {
        return switch (properties.getType()) {
            case FIXED -> new FixedContributionStrategy(properties.getFixed().getPercentage());
            case VARIABLE -> new VariableContributionStrategy(
                    properties.getVariable().getInitialPercentage(),
                    properties.getVariable().getMinPercentage(),
                    properties.getVariable().getThreshold()
            );
        };
    }
}

