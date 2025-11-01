package com.sportygroup.jackpotsystem.contribution.domain.jackpot;

import com.sportygroup.jackpotsystem.contribution.domain.ContributionType;

import java.math.BigDecimal;
import java.util.UUID;

public record Jackpot(
        UUID id,
        String name,
        ContributionType contributionType,
        BigDecimal initialPool,
        BigDecimal fixedPercentage,
        BigDecimal variableInitialPercentage,
        BigDecimal variableMinPercentage,
        BigDecimal variableThreshold
) {
}

