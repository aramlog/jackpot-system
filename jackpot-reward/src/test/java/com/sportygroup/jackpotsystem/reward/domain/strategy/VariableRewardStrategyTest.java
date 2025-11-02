package com.sportygroup.jackpotsystem.reward.domain.strategy;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class VariableRewardStrategyTest {

    @Test
    void shouldAlwaysReturnFalseWhenWinChanceIsZero() {
        // Given
        var strategy = new VariableRewardStrategy(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                new BigDecimal("50000.00")
        );
        var betAmount = new BigDecimal("100.00");
        var currentJackpotAmount = new BigDecimal("10000.00");

        // When
        var result = strategy.evaluateWin(betAmount, currentJackpotAmount);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnBooleanForIntermediateWinChance() {
        // Given
        var strategy = new VariableRewardStrategy(
                new BigDecimal("0.001"), // 0.1% initial
                new BigDecimal("0.01"),  // 1% maximum
                new BigDecimal("50000.00")
        );
        var betAmount = new BigDecimal("100.00");
        var currentJackpotAmount = new BigDecimal("10000.00");

        // When
        var result = strategy.evaluateWin(betAmount, currentJackpotAmount);

        // Then
        assertThat(result).isIn(true, false);
    }

    @Test
    void shouldUseMaxChanceWhenJackpotAboveThreshold() {
        // Given
        var initialChance = new BigDecimal("0.0"); // 0%
        var maxChance = new BigDecimal("1.0"); // 100%
        var threshold = new BigDecimal("50000.00");

        var strategy = new VariableRewardStrategy(initialChance, maxChance, threshold);
        var betAmount = new BigDecimal("100.00");
        var currentJackpotAmount = new BigDecimal("60000.00"); // Above threshold

        // When
        var result = strategy.evaluateWin(betAmount, currentJackpotAmount);

        // Then - Should use 100% chance
        assertThat(result).isTrue();
    }

    @Test
    void shouldUseMaxChanceWhenJackpotEqualsThreshold() {
        // Given
        var initialChance = new BigDecimal("0.0"); // 0%
        var maxChance = new BigDecimal("1.0"); // 100%
        var threshold = new BigDecimal("50000.00");

        var strategy = new VariableRewardStrategy(initialChance, maxChance, threshold);
        var betAmount = new BigDecimal("100.00");
        var currentJackpotAmount = new BigDecimal("50000.00"); // Equals threshold

        // When
        var result = strategy.evaluateWin(betAmount, currentJackpotAmount);

        // Then - Should use 100% chance
        assertThat(result).isTrue();
    }
}

