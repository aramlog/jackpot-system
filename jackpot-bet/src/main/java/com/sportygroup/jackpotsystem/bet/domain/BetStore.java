package com.sportygroup.jackpotsystem.bet.domain;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BetStore {
    Bet save(Bet bet);

    Optional<Bet> findById(UUID betId);

    List<Bet> findByUserId(UUID userId);

    List<Bet> findByJackpotId(UUID jackpotId);

    List<Bet> findBetween(Instant from, Instant to);
}


