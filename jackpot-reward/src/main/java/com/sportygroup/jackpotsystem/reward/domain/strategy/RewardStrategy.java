package com.sportygroup.jackpotsystem.reward.domain.strategy;

import java.math.BigDecimal;

public interface RewardStrategy {
    
    boolean evaluateWin(BigDecimal betAmount, BigDecimal currentJackpotAmount);
}

