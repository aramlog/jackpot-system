package com.sportygroup.jackpotsystem.contribution.domain.jackpot;

import com.sportygroup.jackpotsystem.contribution.domain.ContributionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class CreateJackpotCommand {

    private final JackpotStore jackpotStore;

    public Output execute(Input input) {
        log.info("Creating jackpot with name: {}, type: {}", input.name(), input.contributionType());

        final var jackpot = new Jackpot(
                null,
                input.name(),
                input.contributionType(),
                input.initialPool(),
                input.fixedPercentage(),
                input.variableInitialPercentage(),
                input.variableMinPercentage(),
                input.variableThreshold()
        );

        final var savedJackpot = jackpotStore.save(jackpot);
        log.info("Jackpot created: id={}, name={}", savedJackpot.id(), savedJackpot.name());

        return new Output(savedJackpot.id(), savedJackpot.name());
    }

    public record Input(
            String name,
            ContributionType contributionType,
            BigDecimal initialPool,
            BigDecimal fixedPercentage,
            BigDecimal variableInitialPercentage,
            BigDecimal variableMinPercentage,
            BigDecimal variableThreshold
    ) {
    }

    public record Output(
            UUID jackpotId,
            String name
    ) {
    }
}

