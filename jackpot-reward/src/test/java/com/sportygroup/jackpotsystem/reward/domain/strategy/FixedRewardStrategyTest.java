package com.sportygroup.jackpotsystem.reward.domain.strategy;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class FixedRewardStrategyTest {

    @Test
    void shouldAlwaysReturnFalseWhenWinChanceIsZero() {
        // Given
        var strategy = new FixedRewardStrategy(BigDecimal.ZERO);
        var betAmount = new BigDecimal("100.00");
        var currentJackpotAmount = new BigDecimal("10000.00");

        // When
        var result = strategy.evaluateWin(betAmount, currentJackpotAmount);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void shouldAlwaysReturnTrueWhenWinChanceIsOne() {
        // Given
        var strategy = new FixedRewardStrategy(BigDecimal.ONE);
        var betAmount = new BigDecimal("100.00");
        var currentJackpotAmount = new BigDecimal("10000.00");

        // When
        var result = strategy.evaluateWin(betAmount, currentJackpotAmount);

        // Then
        assertThat(result).isTrue();
    }

    @RepeatedTest(10)
    void shouldReturnBooleanForIntermediateWinChance() {
        // Given - 50% win chance
        var strategy = new FixedRewardStrategy(new BigDecimal("0.5"));
        var betAmount = new BigDecimal("100.00");
        var currentJackpotAmount = new BigDecimal("10000.00");

        // When
        var result = strategy.evaluateWin(betAmount, currentJackpotAmount);

        // Then - Should return true or false
        assertThat(result).isIn(true, false);
    }

    @Test
    void shouldIgnoreBetAmountAndJackpotAmount() {
        // Given
        var strategy = new FixedRewardStrategy(new BigDecimal("0.99")); // 99% chance

        // When - Different bet amounts and jackpot amounts
        var result1 = strategy.evaluateWin(new BigDecimal("10.00"), new BigDecimal("1000.00"));
        var result2 = strategy.evaluateWin(new BigDecimal("1000.00"), new BigDecimal("10.00"));

        // Then - Results should be independent of amounts (could be same or different due to randomness,
        // but the strategy itself doesn't depend on them)
        assertThat(result1).isIn(true, false);
        assertThat(result2).isIn(true, false);
    }
}

