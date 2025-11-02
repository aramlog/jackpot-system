package com.sportygroup.jackpotsystem.reward.domain;

import com.sportygroup.jackpotsystem.core.domain.store.ContributionStore;
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
        log.info("Evaluating reward for betId: {}", input.betId());

        // Get contribution for this bet to get the jackpot amount at the time the bet was placed
        final var contribution = contributionStore.getContributionByBetId(input.betId());
        final var currentJackpotAmount = contribution.currentJackpotAmount();

        log.info("Jackpot amount at bet time: {}", currentJackpotAmount);

        // Evaluate if bet wins using strategy
        final var isWin = rewardStrategy.evaluateWin(contribution.stakeAmount(), currentJackpotAmount);

        JackpotReward savedReward = null;
        if (isWin) {
            log.info("Bet WON! Creating reward for betId: {}", input.betId());

            final var reward = new JackpotReward(
                    null,
                    contribution.betId(),
                    contribution.userId(),
                    contribution.jackpotId(),
                    currentJackpotAmount,
                    Instant.now()
            );

            savedReward = rewardStore.save(reward);
            log.info("Reward saved: id={}, rewardAmount={}", savedReward.id(), savedReward.rewardAmount());

            // Reset jackpot to initial pool value by deleting all contributions
            contributionStore.deleteContributionsByJackpotId(contribution.jackpotId());
            log.info("Jackpot reset by deleting all contributions for jackpotId: {}", contribution.jackpotId());
        } else {
            log.info("Bet did not win jackpot for betId: {}", input.betId());
        }

        return new Output(isWin, savedReward != null ? savedReward.rewardAmount() : BigDecimal.ZERO);
    }

    public record Input(UUID betId) {
    }

    public record Output(
            boolean isWin,
            BigDecimal rewardAmount
    ) {
    }
}

