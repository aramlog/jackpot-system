package com.sportygroup.jackpotsystem.contribution.domain;

import com.sportygroup.jackpotsystem.core.infrastructure.messaging.BetEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
public class ProcessBetEventCommand {

    private final ContributionStore contributionStore;

    public void execute(Input input) {
        final var betEvent = input.betEvent();
        log.info("Processing bet event for betId: {}, jackpotId: {}", betEvent.betId(), betEvent.jackpotId());

        final var contributionPercentage = new BigDecimal("0.05");
        final var contributionAmount = betEvent.betAmount().multiply(contributionPercentage);

        // Calculate current jackpot amount from existing contributions
        final var existingContributions = contributionStore.findByJackpotId(betEvent.jackpotId());
        final var currentJackpotAmount = existingContributions.stream()
                .map(JackpotContribution::contributionAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

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

