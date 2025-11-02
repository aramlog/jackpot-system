package com.sportygroup.jackpotsystem.bet.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class GetBetQuery {

    private final BetStore betStore;

    public Output execute(Input input) {
        log.info("Getting bet by id: {}", input.betId());
        final var bet = betStore.findById(input.betId());
        return new Output(bet);
    }

    public record Input(UUID betId) {
    }

    public record Output(Optional<Bet> bet) {
    }
}

