package com.sportygroup.jackpotsystem.contribution.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record JackpotContribution(
        UUID id,
        UUID betId,
        UUID userId,
        UUID jackpotId,
        BigDecimal stakeAmount,
        BigDecimal contributionAmount,
        BigDecimal currentJackpotAmount,
        Instant createdAt) {
}


