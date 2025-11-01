package com.sportygroup.jackpotsystem.reward.domain.strategy;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Random;

@RequiredArgsConstructor
public class FixedRewardStrategy implements RewardStrategy {

    private final BigDecimal winChance;

    @Override
    public boolean evaluateWin(BigDecimal betAmount, BigDecimal currentJackpotAmount) {
        Random random = new Random();
        double roll = random.nextDouble();
        return roll <= winChance.doubleValue();
    }
}

