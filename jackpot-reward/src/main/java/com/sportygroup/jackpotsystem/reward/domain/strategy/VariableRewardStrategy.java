package com.sportygroup.jackpotsystem.reward.domain.strategy;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@RequiredArgsConstructor
public class VariableRewardStrategy implements RewardStrategy {

    private final BigDecimal initialChance;
    private final BigDecimal maxChance;
    private final BigDecimal threshold;

    @Override
    public boolean evaluateWin(BigDecimal betAmount, BigDecimal currentJackpotAmount) {
        BigDecimal winChance;

        if (currentJackpotAmount.compareTo(threshold) >= 0) {
            // If jackpot exceeds threshold, use maximum chance
            winChance = maxChance;
        } else {
            // Linear increase from initial to maximum chance
            BigDecimal ratio = currentJackpotAmount.divide(threshold, 4, RoundingMode.HALF_UP);
            winChance = initialChance
                    .add(maxChance.subtract(initialChance).multiply(ratio));
        }

        Random random = new Random();
        double roll = random.nextDouble();
        return roll <= winChance.doubleValue();
    }
}

