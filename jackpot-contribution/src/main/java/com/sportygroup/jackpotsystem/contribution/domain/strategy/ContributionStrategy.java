package com.sportygroup.jackpotsystem.contribution.domain.strategy;

import java.math.BigDecimal;

public interface ContributionStrategy {
    
    BigDecimal calculateContribution(BigDecimal betAmount, BigDecimal currentJackpotAmount);
}

