package com.sportygroup.jackpotsystem.contribution.domain.contribution;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class GetContributionQuery {

    private final ContributionStore contributionStore;

    public Output execute(Input input) {
        log.info("Getting contribution for betId: {}", input.betId());
        final var contribution = contributionStore.findByBetId(input.betId());
        return new Output(contribution);
    }

    public record Input(UUID betId) {
    }

    public record Output(Optional<JackpotContribution> contribution) {
    }
}

