package com.sportygroup.jackpotsystem.contribution.domain.jackpot;

import java.util.Optional;
import java.util.UUID;

/**
 * Store interface for jackpot configuration data persistence and retrieval.
 */
public interface JackpotStore {

    /**
     * Saves a jackpot configuration to the store.
     *
     * @param jackpot the jackpot configuration to save
     * @return the saved jackpot with generated identifier
     */
    Jackpot save(Jackpot jackpot);

    /**
     * Retrieves a jackpot configuration by its unique identifier.
     *
     * @param id the jackpot identifier
     * @return an optional containing the jackpot if found, empty otherwise
     */
    Optional<Jackpot> findById(UUID id);
}

