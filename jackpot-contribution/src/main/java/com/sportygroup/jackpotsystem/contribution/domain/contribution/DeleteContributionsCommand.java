package com.sportygroup.jackpotsystem.contribution.domain.contribution;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class DeleteContributionsCommand {

    private final ContributionStore contributionStore;

    public void execute(Input input) {
        log.info("Deleting all contributions for jackpotId: {}", input.jackpotId());
        contributionStore.deleteByJackpotId(input.jackpotId());
    }

    public record Input(UUID jackpotId) {
    }
}

