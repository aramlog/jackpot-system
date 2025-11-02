package com.sportygroup.jackpotsystem.core.domain.messaging;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record BetEvent(
        UUID betId,
        UUID userId,
        UUID jackpotId,
        BigDecimal betAmount,
        Instant createdAt) {
}

