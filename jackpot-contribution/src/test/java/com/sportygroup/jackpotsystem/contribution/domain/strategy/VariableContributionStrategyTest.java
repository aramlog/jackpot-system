package com.sportygroup.jackpotsystem.contribution.domain.strategy;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class VariableContributionStrategyTest {

    @Test
    void shouldUseInitialPercentageWhenJackpotBelowThreshold() {
        // Given
        var initialPercentage = new BigDecimal("0.1"); // 10%
        var minPercentage = new BigDecimal("0.01"); // 1%
        var threshold = new BigDecimal("50000.00");

        var strategy = new VariableContributionStrategy(initialPercentage, minPercentage, threshold);
        var betAmount = new BigDecimal("100.00");
        var currentJackpotAmount = new BigDecimal("10000.00"); // Below threshold

        // When
        var contribution = strategy.calculateContribution(betAmount, currentJackpotAmount);

        // Then - Should use initial percentage (linearly decreased based on progress to threshold)
        // Progress = 10000 / 50000 = 0.2
        // Percentage = 0.1 - (0.1 - 0.01) * 0.2 = 0.1 - 0.018 = 0.082
        // Contribution = 100 * 0.082 = 8.20
        assertThat(contribution).isEqualByComparingTo(new BigDecimal("8.20"));
    }

    @Test
    void shouldUseMinPercentageWhenJackpotAboveThreshold() {
        // Given
        var initialPercentage = new BigDecimal("0.1"); // 10%
        var minPercentage = new BigDecimal("0.01"); // 1%
        var threshold = new BigDecimal("50000.00");

        var strategy = new VariableContributionStrategy(initialPercentage, minPercentage, threshold);
        var betAmount = new BigDecimal("100.00");
        var currentJackpotAmount = new BigDecimal("60000.00"); // Above threshold

        // When
        var contribution = strategy.calculateContribution(betAmount, currentJackpotAmount);

        // Then
        assertThat(contribution).isEqualByComparingTo(new BigDecimal("1.00"));
    }

    @Test
    void shouldUseMinPercentageWhenJackpotEqualsThreshold() {
        // Given
        var initialPercentage = new BigDecimal("0.1"); // 10%
        var minPercentage = new BigDecimal("0.01"); // 1%
        var threshold = new BigDecimal("50000.00");

        var strategy = new VariableContributionStrategy(initialPercentage, minPercentage, threshold);
        var betAmount = new BigDecimal("100.00");
        var currentJackpotAmount = new BigDecimal("50000.00"); // Equals threshold

        // When
        var contribution = strategy.calculateContribution(betAmount, currentJackpotAmount);

        // Then
        assertThat(contribution).isEqualByComparingTo(new BigDecimal("1.00"));
    }

    @Test
    void shouldCalculateLinearDecreaseCorrectly() {
        // Given
        var initialPercentage = new BigDecimal("0.1"); // 10%
        var minPercentage = new BigDecimal("0.01"); // 1%
        var threshold = new BigDecimal("10000.00");

        var strategy = new VariableContributionStrategy(initialPercentage, minPercentage, threshold);
        var betAmount = new BigDecimal("100.00");
        var currentJackpotAmount = new BigDecimal("5000.00"); // Half of threshold

        // When
        var contribution = strategy.calculateContribution(betAmount, currentJackpotAmount);

        // Then - At 50% of threshold
        // Progress = 5000 / 10000 = 0.5
        // Percentage = 0.1 - (0.1 - 0.01) * 0.5 = 0.1 - 0.045 = 0.055
        // Contribution = 100 * 0.055 = 5.50
        assertThat(contribution).isEqualByComparingTo(new BigDecimal("5.50"));
    }
}

