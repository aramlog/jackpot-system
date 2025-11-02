package com.sportygroup.jackpotsystem.contribution.domain.contribution;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContributionStore {

    JackpotContribution save(JackpotContribution contribution);

    List<JackpotContribution> findByJackpotId(UUID jackpotId);

    Optional<JackpotContribution> findByBetId(UUID betId);

    void deleteByJackpotId(UUID jackpotId);
}


