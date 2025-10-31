package com.sportygroup.jackpotsystem.bet.domain;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
public class PlaceBetCommand {

    private final BetStore betStore;
    // TODO: Add Kafka publisher when ready
    // private final BetEventPublisher betEventPublisher;

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

        // TODO: Publish bet event to Kafka
        // betEventPublisher.publish(savedBet);

        return Output.of(savedBet);
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
        public static Output of(Bet bet) {
            return new Output(
                    bet.betId(),
                    "jackpot-bets" // TODO: Move to configuration
            );
        }
    }
}

