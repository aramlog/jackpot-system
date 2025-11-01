package com.sportygroup.jackpotsystem.core.domain.store;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface ContributionStore {

    List<JackpotContributionResult> getContributionsByJackpotId(UUID jackpotId);

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

