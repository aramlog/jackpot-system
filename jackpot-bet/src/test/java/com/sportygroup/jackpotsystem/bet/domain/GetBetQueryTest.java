package com.sportygroup.jackpotsystem.bet.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetBetQueryTest {

    @Mock
    private BetStore betStore;

    private GetBetQuery getBetQuery;

    @BeforeEach
    void setUp() {
        getBetQuery = new GetBetQuery(betStore);
    }

    @Test
    void shouldReturnBetWhenFound() {
        // Given
        var betId = UUID.randomUUID();
        var userId = UUID.randomUUID();
        var jackpotId = UUID.randomUUID();
        var betAmount = new BigDecimal("100.00");
        var createdAt = Instant.now();

        var bet = new Bet(betId, userId, jackpotId, betAmount, createdAt);
        when(betStore.findById(betId)).thenReturn(Optional.of(bet));

        var input = new GetBetQuery.Input(betId);

        // When
        var output = getBetQuery.execute(input);

        // Then
        assertThat(output.bet()).isPresent();
        assertThat(output.bet().get()).isEqualTo(bet);
        verify(betStore).findById(betId);
    }

    @Test
    void shouldReturnEmptyWhenBetNotFound() {
        // Given
        var betId = UUID.randomUUID();
        when(betStore.findById(betId)).thenReturn(Optional.empty());

        var input = new GetBetQuery.Input(betId);

        // When
        var output = getBetQuery.execute(input);

        // Then
        assertThat(output.bet()).isEmpty();
        verify(betStore).findById(betId);
    }
}

