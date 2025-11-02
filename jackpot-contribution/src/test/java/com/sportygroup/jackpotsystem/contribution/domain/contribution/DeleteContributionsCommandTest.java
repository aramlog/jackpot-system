package com.sportygroup.jackpotsystem.contribution.domain.contribution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteContributionsCommandTest {

    @Mock
    private ContributionStore contributionStore;

    private DeleteContributionsCommand deleteContributionsCommand;

    @BeforeEach
    void setUp() {
        deleteContributionsCommand = new DeleteContributionsCommand(contributionStore);
    }

    @Test
    void shouldDeleteContributionsByJackpotId() {
        // Given
        var jackpotId = UUID.randomUUID();
        var input = new DeleteContributionsCommand.Input(jackpotId);

        // When
        deleteContributionsCommand.execute(input);

        // Then
        verify(contributionStore).deleteByJackpotId(jackpotId);
    }
}

