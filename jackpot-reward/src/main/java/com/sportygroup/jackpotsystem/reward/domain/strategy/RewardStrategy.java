package com.sportygroup.jackpotsystem.reward.domain.strategy;

import java.math.BigDecimal;

/**
 * Strategy interface for evaluating whether a bet wins the jackpot.
 * Different implementations provide various win evaluation algorithms (e.g., fixed chance, variable based on jackpot size).
 */
public interface RewardStrategy {

    /**
     * Evaluates whether a bet wins the jackpot.
     *
     * @param betAmount             the bet amount
     * @param currentJackpotAmount  the current total jackpot amount
     * @return true if the bet wins, false otherwise
     */
    boolean evaluateWin(BigDecimal betAmount, BigDecimal currentJackpotAmount);
}

