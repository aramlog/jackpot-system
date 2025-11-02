package com.sportygroup.jackpotsystem.core.domain.store;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface BetStore {

    Optional<BetResult> findById(UUID betId);

    record BetResult(
            UUID betId,
            UUID userId,
            UUID jackpotId,
            BigDecimal betAmount,
            Instant createdAt
    ) {
    }
}

