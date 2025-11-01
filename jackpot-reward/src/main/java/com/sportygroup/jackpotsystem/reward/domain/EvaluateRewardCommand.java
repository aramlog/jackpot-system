package com.sportygroup.jackpotsystem.reward.domain;

import com.sportygroup.jackpotsystem.core.domain.store.ContributionStore;
import com.sportygroup.jackpotsystem.core.domain.store.ContributionStore.JackpotContributionResult;
import com.sportygroup.jackpotsystem.reward.domain.strategy.RewardStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class EvaluateRewardCommand {

    private final ContributionStore contributionStore;
    private final RewardStore rewardStore;
    private final RewardStrategy rewardStrategy;

    public Output execute(Input input) {
        log.info("Evaluating reward for betId: {}, jackpotId: {}", input.betId(), input.jackpotId());

        // Get current jackpot amount from contributions
        final var existingContributions = contributionStore.getContributionsByJackpotId(input.jackpotId());
        final var currentJackpotAmount = existingContributions.stream()
                .map(JackpotContributionResult::contributionAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        log.info("Current jackpot amount: {}", currentJackpotAmount);

        // Evaluate if bet wins using strategy
        final var isWin = rewardStrategy.evaluateWin(input.betAmount(), currentJackpotAmount);

        JackpotReward savedReward = null;
        if (isWin) {
            log.info("Bet WON! Creating reward for betId: {}", input.betId());

            final var reward = new JackpotReward(
                    null,
                    input.betId(),
                    input.userId(),
                    input.jackpotId(),
                    currentJackpotAmount,
                    Instant.now()
            );

            savedReward = rewardStore.save(reward);
            log.info("Reward saved: id={}, rewardAmount={}", savedReward.id(), savedReward.rewardAmount());

            // Reset jackpot to initial pool value by deleting all contributions
            contributionStore.deleteContributionsByJackpotId(input.jackpotId());
            log.info("Jackpot reset by deleting all contributions for jackpotId: {}", input.jackpotId());
        } else {
            log.info("Bet did not win jackpot for betId: {}", input.betId());
        }

        return new Output(isWin, savedReward != null ? savedReward.rewardAmount() : BigDecimal.ZERO);
    }

    public record Input(
            UUID betId,
            UUID userId,
            UUID jackpotId,
            BigDecimal betAmount
    ) {
    }

    public record Output(
            boolean isWin,
            BigDecimal rewardAmount
    ) {
    }
}

