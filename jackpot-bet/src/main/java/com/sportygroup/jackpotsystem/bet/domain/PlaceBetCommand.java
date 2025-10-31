package com.sportygroup.jackpotsystem.bet.domain;

import com.sportygroup.jackpotsystem.bet.infrastructure.messaging.BetEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class PlaceBetCommand {

    private final BetStore betStore;
    private final BetEventPublisher betEventPublisher;

    public Output execute(Input input) {
        final var now = Instant.now();
        final var bet = new Bet(
                null,
                input.userId(),
                input.jackpotId(),
                input.betAmount(),
                now
        );


        final var savedBet = betStore.save(bet);

        betEventPublisher.publish(savedBet);

        return Output.of(savedBet, betEventPublisher.getTopic());
    }

    public record Input(
            UUID userId,
            UUID jackpotId,
            BigDecimal betAmount
    ) {
    }

    public record Output(
            UUID betId,
            String topic
    ) {
        public static Output of(Bet bet, String topic) {
            return new Output(bet.betId(), topic);
        }
    }
}

