package com.sportygroup.jackpotsystem.contribution.domain;

import com.sportygroup.jackpotsystem.core.infrastructure.messaging.BetEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
public class ProcessBetEventCommand {

    private final ContributionStore contributionStore;
    private final ContributionStrategy contributionStrategy;

    public void execute(Input input) {
        final var betEvent = input.betEvent();
        log.info("Processing bet event for betId: {}, jackpotId: {}", betEvent.betId(), betEvent.jackpotId());

        // Calculate current jackpot amount from existing contributions
        final var existingContributions = contributionStore.findByJackpotId(betEvent.jackpotId());
        final var currentJackpotAmount = existingContributions.stream()
                .map(JackpotContribution::contributionAmount)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

        // Use strategy to calculate contribution amount
        final var contributionAmount = contributionStrategy.calculateContribution(
                betEvent.betAmount(),
                currentJackpotAmount
        );

        final var contribution = new JackpotContribution(
                null,
                betEvent.betId(),
                betEvent.userId(),
                betEvent.jackpotId(),
                betEvent.betAmount(),
                contributionAmount,
                currentJackpotAmount,
                Instant.now()
        );

        final var savedContribution = contributionStore.save(contribution);
        log.info("Contribution saved: id={}, contributionAmount={}, currentJackpotAmount={}",
                savedContribution.id(), savedContribution.contributionAmount(), savedContribution.currentJackpotAmount());
    }

    public record Input(BetEvent betEvent) {
    }
}

