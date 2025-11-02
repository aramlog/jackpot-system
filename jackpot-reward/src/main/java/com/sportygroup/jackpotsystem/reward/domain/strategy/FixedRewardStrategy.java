package com.sportygroup.jackpotsystem.reward.domain.strategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
public class FixedRewardStrategy implements RewardStrategy {

    private final BigDecimal winChance;

    @Override
    public boolean evaluateWin(BigDecimal betAmount, BigDecimal currentJackpotAmount) {
        Random random = new Random();
        double roll = random.nextDouble();
        boolean win = roll <= winChance.doubleValue();
        log.info("FixedRewardStrategy: winChance={}, roll={}, result={}", winChance, roll, win);
        return win;
    }
}

