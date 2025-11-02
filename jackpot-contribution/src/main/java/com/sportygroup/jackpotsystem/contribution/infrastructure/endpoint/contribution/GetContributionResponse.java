package com.sportygroup.jackpotsystem.contribution.infrastructure.endpoint.contribution;

import com.sportygroup.jackpotsystem.contribution.domain.contribution.GetContributionQuery;
import com.sportygroup.jackpotsystem.core.exception.NotFoundException;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Value
public class GetContributionResponse {

    UUID id;
    UUID betId;
    UUID userId;
    UUID jackpotId;
    BigDecimal stakeAmount;
    BigDecimal contributionAmount;
    BigDecimal currentJackpotAmount;
    Instant createdAt;

    public static GetContributionResponse of(GetContributionQuery.Output output) {
        final var contribution = output.contribution()
                .orElseThrow(() -> new NotFoundException("Contribution not found"));
        return new GetContributionResponse(
                contribution.id(),
                contribution.betId(),
                contribution.userId(),
                contribution.jackpotId(),
                contribution.stakeAmount(),
                contribution.contributionAmount(),
                contribution.currentJackpotAmount(),
                contribution.createdAt()
        );
    }
}

