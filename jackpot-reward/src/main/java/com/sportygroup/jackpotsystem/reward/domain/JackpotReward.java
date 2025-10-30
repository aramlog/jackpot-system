package com.sportygroup.jackpotsystem.reward.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record JackpotReward(
        UUID id,
        UUID betId,
        UUID userId,
        UUID jackpotId,
        BigDecimal rewardAmount,
        Instant createdAt) {
}


