package com.sportygroup.jackpotsystem.reward.domain;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Store interface for jackpot reward data persistence and retrieval.
 */
public interface RewardStore {

    /**
     * Saves a reward to the store.
     *
     * @param reward the reward to save
     * @return the saved reward with generated identifier
     */
    JackpotReward save(JackpotReward reward);

}


