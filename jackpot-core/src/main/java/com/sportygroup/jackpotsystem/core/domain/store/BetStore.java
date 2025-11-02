package com.sportygroup.jackpotsystem.core.domain.store;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * Store interface for retrieving bet data across modules.
 */
public interface BetStore {

    /**
     * Retrieves a bet by its unique identifier.
     *
     * @param betId the bet identifier
     * @return an optional containing the bet if found, empty otherwise
     */
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

