package com.sportygroup.jackpotsystem.reward.domain;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RewardStore {
    JackpotReward save(JackpotReward reward);

    Optional<JackpotReward> findById(UUID id);

    List<JackpotReward> findByJackpotId(UUID jackpotId);

    List<JackpotReward> findByBetId(UUID betId);

    List<JackpotReward> findBetween(Instant from, Instant to);
}


