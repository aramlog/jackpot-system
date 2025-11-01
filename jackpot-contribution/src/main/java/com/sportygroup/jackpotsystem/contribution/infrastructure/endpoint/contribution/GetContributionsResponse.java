package com.sportygroup.jackpotsystem.contribution.infrastructure.endpoint.contribution;

import com.sportygroup.jackpotsystem.contribution.domain.contribution.GetContributionsQuery;
import com.sportygroup.jackpotsystem.contribution.domain.contribution.JackpotContribution;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record GetContributionsResponse(List<ContributionItem> contributions) {

    public static GetContributionsResponse of(GetContributionsQuery.Output output) {
        final var items = output.contributions().stream()
                .map(ContributionItem::fromDomain)
                .collect(Collectors.toList());
        return new GetContributionsResponse(items);
    }

    public record ContributionItem(
            UUID id,
            UUID betId,
            UUID userId,
            UUID jackpotId,
            BigDecimal stakeAmount,
            BigDecimal contributionAmount,
            BigDecimal currentJackpotAmount,
            Instant createdAt) {

        public static ContributionItem fromDomain(JackpotContribution contribution) {
            return new ContributionItem(
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
}

