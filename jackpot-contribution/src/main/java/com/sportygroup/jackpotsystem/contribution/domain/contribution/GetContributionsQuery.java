package com.sportygroup.jackpotsystem.contribution.domain.contribution;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class GetContributionsQuery {

    private final ContributionStore contributionStore;

    public Output execute(Input input) {
        final var contributions = contributionStore.findByJackpotId(input.jackpotId());
        return Output.of(contributions);
    }

    public record Input(UUID jackpotId) {
    }

    public record Output(List<JackpotContribution> contributions) {
        public static Output of(List<JackpotContribution> contributions) {
            return new Output(contributions);
        }
    }
}

