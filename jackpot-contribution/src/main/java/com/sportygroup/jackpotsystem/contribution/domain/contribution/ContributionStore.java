package com.sportygroup.jackpotsystem.contribution.domain.contribution;

import java.util.List;
import java.util.UUID;

public interface ContributionStore {

    JackpotContribution save(JackpotContribution contribution);

    List<JackpotContribution> findByJackpotId(UUID jackpotId);

    void deleteByJackpotId(UUID jackpotId);
}


