package com.sportygroup.jackpotsystem.contribution.domain.strategy;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
public class FixedContributionStrategy implements ContributionStrategy {

    private final BigDecimal percentage;

    @Override
    public BigDecimal calculateContribution(BigDecimal betAmount, BigDecimal currentJackpotAmount) {
        return betAmount.multiply(percentage)
                .setScale(2, RoundingMode.HALF_UP);
    }
}

