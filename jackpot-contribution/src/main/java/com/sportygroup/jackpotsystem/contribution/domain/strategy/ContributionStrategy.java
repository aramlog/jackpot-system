package com.sportygroup.jackpotsystem.contribution.domain.strategy;

import java.math.BigDecimal;

/**
 * Strategy interface for calculating contribution amounts to jackpots.
 * Different implementations provide various contribution calculation algorithms (e.g., fixed percentage, variable).
 */
public interface ContributionStrategy {

    /**
     * Calculates the contribution amount for a bet.
     *
     * @param betAmount             the bet amount
     * @param currentJackpotAmount  the current total jackpot amount
     * @return the contribution amount to add to the jackpot
     */
    BigDecimal calculateContribution(BigDecimal betAmount, BigDecimal currentJackpotAmount);
}

