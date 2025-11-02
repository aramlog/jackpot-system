package com.sportygroup.jackpotsystem.bet.domain;

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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaceBetCommandTest {

    @Mock
    private BetStore betStore;

    @Mock
    private BetPublisher betPublisher;

    private PlaceBetCommand placeBetCommand;

    @BeforeEach
    void setUp() {
        placeBetCommand = new PlaceBetCommand(betStore, betPublisher);
    }

    @Test
    void shouldPlaceBetAndPublishEvent() {
        // Given
        var input = new PlaceBetCommand.Input(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new BigDecimal("100.00")
        );

        var savedBet = new Bet(
                UUID.randomUUID(),
                input.userId(),
                input.jackpotId(),
                input.betAmount(),
                Instant.now()
        );

        when(betStore.save(any(Bet.class))).thenReturn(savedBet);
        when(betPublisher.getTopic()).thenReturn("jackpot-bets");

        // When
        var output = placeBetCommand.execute(input);

        // Then
        assertThat(output.betId()).isEqualTo(savedBet.betId());
        assertThat(output.topic()).isEqualTo("jackpot-bets");

        verify(betStore).save(any(Bet.class));
        verify(betPublisher).publish(savedBet);
        verify(betPublisher).getTopic();
    }

    @Test
    void shouldCreateBetWithCorrectInputData() {
        // Given
        var userId = UUID.randomUUID();
        var jackpotId = UUID.randomUUID();
        var betAmount = new BigDecimal("50.00");
        var input = new PlaceBetCommand.Input(userId, jackpotId, betAmount);

        var savedBet = new Bet(
                UUID.randomUUID(),
                userId,
                jackpotId,
                betAmount,
                Instant.now()
        );

        when(betStore.save(any(Bet.class))).thenReturn(savedBet);
        when(betPublisher.getTopic()).thenReturn("jackpot-bets");

        // When
        placeBetCommand.execute(input);

        // Then
        ArgumentCaptor<Bet> betCaptor = ArgumentCaptor.forClass(Bet.class);
        verify(betStore).save(betCaptor.capture());

        var capturedBet = betCaptor.getValue();
        assertThat(capturedBet.betId()).isNull();
        assertThat(capturedBet.userId()).isEqualTo(userId);
        assertThat(capturedBet.jackpotId()).isEqualTo(jackpotId);
        assertThat(capturedBet.betAmount()).isEqualTo(betAmount);
    }
}

