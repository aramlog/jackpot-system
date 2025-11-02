package com.sportygroup.jackpotsystem.contribution.domain;

import com.sportygroup.jackpotsystem.contribution.domain.contribution.ContributionStore;
import com.sportygroup.jackpotsystem.contribution.domain.contribution.JackpotContribution;
import com.sportygroup.jackpotsystem.contribution.domain.jackpot.Jackpot;
import com.sportygroup.jackpotsystem.contribution.domain.jackpot.JackpotStore;
import com.sportygroup.jackpotsystem.contribution.domain.strategy.ContributionStrategy;
import com.sportygroup.jackpotsystem.contribution.domain.strategy.ContributionStrategyFactory;
import com.sportygroup.jackpotsystem.core.domain.messaging.BetEvent;
import com.sportygroup.jackpotsystem.core.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessBetEventCommandTest {

    @Mock
    private ContributionStore contributionStore;

    @Mock
    private JackpotStore jackpotStore;

    @Mock
    private ContributionStrategyFactory contributionStrategyFactory;

    @Mock
    private ContributionStrategy contributionStrategy;

    private ProcessBetEventCommand processBetEventCommand;

    @BeforeEach
    void setUp() {
        processBetEventCommand = new ProcessBetEventCommand(
                contributionStore,
                jackpotStore,
                contributionStrategyFactory
        );
    }

    @Test
    void shouldProcessBetEventAndCreateContribution() {
        // Given
        var betId = UUID.randomUUID();
        var userId = UUID.randomUUID();
        var jackpotId = UUID.randomUUID();
        var betAmount = new BigDecimal("100.00");

        var betEvent = new BetEvent(betId, userId, jackpotId, betAmount, Instant.now());
        var input = new ProcessBetEventCommand.Input(betEvent);

        var jackpot = new Jackpot(
                jackpotId,
                "Test Jackpot",
                ContributionType.FIXED,
                new BigDecimal("1000.00"),
                new BigDecimal("0.05"),
                null,
                null,
                null
        );

        when(jackpotStore.findById(jackpotId)).thenReturn(Optional.of(jackpot));
        when(contributionStrategyFactory.create(jackpot)).thenReturn(contributionStrategy);
        when(contributionStore.findByJackpotId(jackpotId)).thenReturn(List.of());

        var expectedContribution = new BigDecimal("5.00");
        when(contributionStrategy.calculateContribution(betAmount, jackpot.initialPool()))
                .thenReturn(expectedContribution);

        var savedContribution = new JackpotContribution(
                UUID.randomUUID(),
                betId,
                userId,
                jackpotId,
                betAmount,
                expectedContribution,
                jackpot.initialPool(),
                Instant.now()
        );
        when(contributionStore.save(any(JackpotContribution.class))).thenReturn(savedContribution);

        // When
        processBetEventCommand.execute(input);

        // Then
        verify(jackpotStore).findById(jackpotId);
        verify(contributionStrategyFactory).create(jackpot);
        verify(contributionStore).findByJackpotId(jackpotId);
        verify(contributionStrategy).calculateContribution(betAmount, jackpot.initialPool());
        verify(contributionStore).save(any(JackpotContribution.class));

        ArgumentCaptor<JackpotContribution> contributionCaptor = ArgumentCaptor.forClass(JackpotContribution.class);
        verify(contributionStore).save(contributionCaptor.capture());

        var capturedContribution = contributionCaptor.getValue();
        assertThat(capturedContribution.betId()).isEqualTo(betId);
        assertThat(capturedContribution.userId()).isEqualTo(userId);
        assertThat(capturedContribution.jackpotId()).isEqualTo(jackpotId);
        assertThat(capturedContribution.stakeAmount()).isEqualTo(betAmount);
        assertThat(capturedContribution.contributionAmount()).isEqualTo(expectedContribution);
        assertThat(capturedContribution.currentJackpotAmount()).isEqualTo(jackpot.initialPool());
    }

    @Test
    void shouldThrowExceptionWhenJackpotNotFound() {
        // Given
        var betId = UUID.randomUUID();
        var userId = UUID.randomUUID();
        var jackpotId = UUID.randomUUID();
        var betAmount = new BigDecimal("100.00");

        var betEvent = new BetEvent(betId, userId, jackpotId, betAmount, Instant.now());
        var input = new ProcessBetEventCommand.Input(betEvent);

        when(jackpotStore.findById(jackpotId)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> processBetEventCommand.execute(input))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Jackpot not found");

        verify(jackpotStore).findById(jackpotId);
        verify(contributionStore, never()).save(any());
    }

    @Test
    void shouldCalculateCurrentJackpotWithExistingContributions() {
        // Given
        var betId = UUID.randomUUID();
        var userId = UUID.randomUUID();
        var jackpotId = UUID.randomUUID();
        var betAmount = new BigDecimal("100.00");

        var betEvent = new BetEvent(betId, userId, jackpotId, betAmount, Instant.now());
        var input = new ProcessBetEventCommand.Input(betEvent);

        var jackpot = new Jackpot(
                jackpotId,
                "Test Jackpot",
                ContributionType.FIXED,
                new BigDecimal("1000.00"),
                new BigDecimal("0.05"),
                null,
                null,
                null
        );

        var existingContribution = new JackpotContribution(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                jackpotId,
                new BigDecimal("200.00"),
                new BigDecimal("10.00"),
                jackpot.initialPool(),
                Instant.now()
        );

        when(jackpotStore.findById(jackpotId)).thenReturn(Optional.of(jackpot));
        when(contributionStrategyFactory.create(jackpot)).thenReturn(contributionStrategy);
        when(contributionStore.findByJackpotId(jackpotId)).thenReturn(List.of(existingContribution));

        var currentJackpotAmount = new BigDecimal("1010.00"); // initialPool + existing contribution
        when(contributionStrategy.calculateContribution(betAmount, currentJackpotAmount))
                .thenReturn(new BigDecimal("50.00"));

        when(contributionStore.save(any(JackpotContribution.class)))
                .thenReturn(new JackpotContribution(
                        UUID.randomUUID(),
                        betId,
                        userId,
                        jackpotId,
                        betAmount,
                        new BigDecimal("50.00"),
                        currentJackpotAmount,
                        Instant.now()
                ));

        // When
        processBetEventCommand.execute(input);

        // Then
        verify(contributionStrategy).calculateContribution(betAmount, currentJackpotAmount);
    }
}

