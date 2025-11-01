package com.sportygroup.jackpotsystem.contribution.domain.strategy;

import com.sportygroup.jackpotsystem.contribution.domain.jackpot.Jackpot;

public class ContributionStrategyFactory {

    public ContributionStrategy create(Jackpot jackpot) {
        return switch (jackpot.contributionType()) {
            case FIXED -> new FixedContributionStrategy(jackpot.fixedPercentage());
            case VARIABLE -> new VariableContributionStrategy(
                    jackpot.variableInitialPercentage(),
                    jackpot.variableMinPercentage(),
                    jackpot.variableThreshold()
            );
        };
    }
}

