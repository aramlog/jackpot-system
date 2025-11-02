package com.sportygroup.jackpotsystem.core.domain.store;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Store interface for retrieving and managing contribution data across modules.
 */
public interface ContributionStore {

    /**
     * Retrieves all contributions for a specific jackpot.
     *
     * @param jackpotId the jackpot identifier
     * @return list of contributions for the jackpot
     */
    List<JackpotContributionResult> getContributionsByJackpotId(UUID jackpotId);

    /**
     * Retrieves the contribution associated with a specific bet.
     *
     * @param betId the bet identifier
     * @return the contribution for the bet
     */
    JackpotContributionResult getContributionByBetId(UUID betId);

    /**
     * Deletes all contributions for a specific jackpot (used when jackpot is won).
     *
     * @param jackpotId the jackpot identifier
     */
    void deleteContributionsByJackpotId(UUID jackpotId);

    record JackpotContributionResult(
            UUID id,
            UUID betId,
            UUID userId,
            UUID jackpotId,
            BigDecimal stakeAmount,
            BigDecimal contributionAmount,
            BigDecimal currentJackpotAmount,
            Instant createdAt) {
    }
}

