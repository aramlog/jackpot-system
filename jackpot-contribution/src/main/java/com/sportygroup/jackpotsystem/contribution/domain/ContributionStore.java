package com.sportygroup.jackpotsystem.contribution.domain;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContributionStore {
    JackpotContribution save(JackpotContribution contribution);
    Optional<JackpotContribution> findById(UUID id);
    List<JackpotContribution> findByJackpotId(UUID jackpotId);
    List<JackpotContribution> findByBetId(UUID betId);
    List<JackpotContribution> findBetween(Instant from, Instant to);
}


