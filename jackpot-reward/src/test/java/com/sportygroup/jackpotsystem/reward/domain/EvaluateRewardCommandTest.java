package com.sportygroup.jackpotsystem.reward.domain;

import com.sportygroup.jackpotsystem.core.domain.store.ContributionStore;
import com.sportygroup.jackpotsystem.core.domain.store.ContributionStore.JackpotContributionResult;
import com.sportygroup.jackpotsystem.reward.domain.strategy.RewardStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EvaluateRewardCommandTest {

    @Mock
    private ContributionStore contributionStore;

    @Mock
    private RewardStore rewardStore;

    @Mock
    private RewardStrategy rewardStrategy;

    private EvaluateRewardCommand evaluateRewardCommand;

    @BeforeEach
    void setUp() {
        evaluateRewardCommand = new EvaluateRewardCommand(
                contributionStore,
                rewardStore,
                rewardStrategy
        );
    }

    @Test
    void shouldSaveRewardAndDeleteContributionsWhenWin() {
        // Given
        var betId = UUID.randomUUID();
        var userId = UUID.randomUUID();
        var jackpotId = UUID.randomUUID();
        var stakeAmount = new BigDecimal("100.00");
        var jackpotAmount = new BigDecimal("10000.00");

        var contribution = new JackpotContributionResult(
                UUID.randomUUID(),
                betId,
                userId,
                jackpotId,
                stakeAmount,
                new BigDecimal("10.00"),
                jackpotAmount,
                Instant.now()
        );

        when(contributionStore.getContributionByBetId(betId)).thenReturn(contribution);
        when(rewardStrategy.evaluateWin(stakeAmount, jackpotAmount)).thenReturn(true);

        var savedReward = new JackpotReward(
                UUID.randomUUID(),
                betId,
                userId,
                jackpotId,
                jackpotAmount,
                Instant.now()
        );
        when(rewardStore.save(any(JackpotReward.class))).thenReturn(savedReward);

        var input = new EvaluateRewardCommand.Input(betId);

        // When
        var output = evaluateRewardCommand.execute(input);

        // Then
        assertThat(output.isWin()).isTrue();
        assertThat(output.rewardAmount()).isEqualByComparingTo(jackpotAmount);

        verify(contributionStore).getContributionByBetId(betId);
        verify(rewardStrategy).evaluateWin(stakeAmount, jackpotAmount);
        verify(rewardStore).save(any(JackpotReward.class));
        verify(contributionStore).deleteContributionsByJackpotId(jackpotId);

        ArgumentCaptor<JackpotReward> rewardCaptor = ArgumentCaptor.forClass(JackpotReward.class);
        verify(rewardStore).save(rewardCaptor.capture());

        var capturedReward = rewardCaptor.getValue();
        assertThat(capturedReward.betId()).isEqualTo(betId);
        assertThat(capturedReward.userId()).isEqualTo(userId);
        assertThat(capturedReward.jackpotId()).isEqualTo(jackpotId);
        assertThat(capturedReward.rewardAmount()).isEqualTo(jackpotAmount);
    }

    @Test
    void shouldReturnZeroRewardWhenNoWin() {
        // Given
        var betId = UUID.randomUUID();
        var userId = UUID.randomUUID();
        var jackpotId = UUID.randomUUID();
        var stakeAmount = new BigDecimal("100.00");
        var jackpotAmount = new BigDecimal("10000.00");

        var contribution = new JackpotContributionResult(
                UUID.randomUUID(),
                betId,
                userId,
                jackpotId,
                stakeAmount,
                new BigDecimal("10.00"),
                jackpotAmount,
                Instant.now()
        );

        when(contributionStore.getContributionByBetId(betId)).thenReturn(contribution);
        when(rewardStrategy.evaluateWin(stakeAmount, jackpotAmount)).thenReturn(false);

        var input = new EvaluateRewardCommand.Input(betId);

        // When
        var output = evaluateRewardCommand.execute(input);

        // Then
        assertThat(output.isWin()).isFalse();
        assertThat(output.rewardAmount()).isEqualByComparingTo(BigDecimal.ZERO);

        verify(contributionStore).getContributionByBetId(betId);
        verify(rewardStrategy).evaluateWin(stakeAmount, jackpotAmount);
        verify(rewardStore, never()).save(any());
        verify(contributionStore, never()).deleteContributionsByJackpotId(any());
    }
}

