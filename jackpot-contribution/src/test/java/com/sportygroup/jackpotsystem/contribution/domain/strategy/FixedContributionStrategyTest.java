package com.sportygroup.jackpotsystem.contribution.domain.strategy;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class FixedContributionStrategyTest {

    @Test
    void shouldCalculateContributionWithFixedPercentage() {
        // Given
        var percentage = new BigDecimal("0.05"); // 5%
        var strategy = new FixedContributionStrategy(percentage);
        var betAmount = new BigDecimal("100.00");
        var currentJackpotAmount = new BigDecimal("1000.00");

        // When
        var contribution = strategy.calculateContribution(betAmount, currentJackpotAmount);

        // Then
        assertThat(contribution).isEqualByComparingTo(new BigDecimal("5.00"));
    }

    @Test
    void shouldRoundContributionToTwoDecimals() {
        // Given
        var percentage = new BigDecimal("0.033"); // 3.3%
        var strategy = new FixedContributionStrategy(percentage);
        var betAmount = new BigDecimal("100.00");
        var currentJackpotAmount = new BigDecimal("1000.00");

        // When
        var contribution = strategy.calculateContribution(betAmount, currentJackpotAmount);

        // Then
        assertThat(contribution).isEqualByComparingTo(new BigDecimal("3.30"));
    }

    @Test
    void shouldHandleLargeBetAmounts() {
        // Given
        var percentage = new BigDecimal("0.1"); // 10%
        var strategy = new FixedContributionStrategy(percentage);
        var betAmount = new BigDecimal("5000.00");
        var currentJackpotAmount = new BigDecimal("10000.00");

        // When
        var contribution = strategy.calculateContribution(betAmount, currentJackpotAmount);

        // Then
        assertThat(contribution).isEqualByComparingTo(new BigDecimal("500.00"));
    }
}

