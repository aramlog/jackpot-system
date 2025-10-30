package com.sportygroup.jackpotsystem.bet.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record Bet(
        UUID betId,
        UUID userId,
        UUID jackpotId,
        BigDecimal betAmount,
        Instant createdAt) {
}


