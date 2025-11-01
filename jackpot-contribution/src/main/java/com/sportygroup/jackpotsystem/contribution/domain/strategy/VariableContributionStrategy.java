package com.sportygroup.jackpotsystem.contribution.domain.strategy;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
public class VariableContributionStrategy implements ContributionStrategy {

    private final BigDecimal initialPercentage;
    private final BigDecimal minPercentage;
    private final BigDecimal threshold;

    @Override
    public BigDecimal calculateContribution(BigDecimal betAmount, BigDecimal currentJackpotAmount) {
        BigDecimal percentage;

        if (currentJackpotAmount.compareTo(threshold) >= 0) {
            // If jackpot exceeds threshold, use minimum percentage
            percentage = minPercentage;
        } else {
            // Linear decrease from initial to minimum percentage
            BigDecimal ratio = currentJackpotAmount.divide(threshold, 4, RoundingMode.HALF_UP);
            percentage = initialPercentage
                    .subtract(initialPercentage.subtract(minPercentage).multiply(ratio));
        }

        return betAmount.multiply(percentage)
                .setScale(2, RoundingMode.HALF_UP);
    }
}

