package com.sportygroup.jackpotsystem.bet.domain;

import java.util.Optional;
import java.util.UUID;

/**
 * Store interface for bet data persistence and retrieval.
 */
public interface BetStore {

    /**
     * Saves a bet to the store.
     *
     * @param bet the bet to save
     * @return the saved bet with generated identifier
     */
    Bet save(Bet bet);

    /**
     * Retrieves a bet by its unique identifier.
     *
     * @param betId the bet identifier
     * @return an optional containing the bet if found, empty otherwise
     */
    Optional<Bet> findById(UUID betId);
}


