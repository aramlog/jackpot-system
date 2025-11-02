package com.sportygroup.jackpotsystem.contribution.domain.contribution;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Store interface for jackpot contribution data persistence and retrieval.
 */
public interface ContributionStore {

    /**
     * Saves a contribution to the store.
     *
     * @param contribution the contribution to save
     * @return the saved contribution with generated identifier
     */
    JackpotContribution save(JackpotContribution contribution);

    /**
     * Retrieves all contributions for a specific jackpot.
     *
     * @param jackpotId the jackpot identifier
     * @return list of contributions for the jackpot, ordered by creation time
     */
    List<JackpotContribution> findByJackpotId(UUID jackpotId);

    /**
     * Retrieves the contribution associated with a specific bet.
     *
     * @param betId the bet identifier
     * @return an optional containing the contribution if found, empty otherwise
     */
    Optional<JackpotContribution> findByBetId(UUID betId);

    /**
     * Deletes all contributions for a specific jackpot (used when jackpot is won).
     *
     * @param jackpotId the jackpot identifier
     */
    void deleteByJackpotId(UUID jackpotId);
}


