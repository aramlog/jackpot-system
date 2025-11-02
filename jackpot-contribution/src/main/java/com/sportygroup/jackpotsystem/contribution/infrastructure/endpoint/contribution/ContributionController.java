package com.sportygroup.jackpotsystem.contribution.infrastructure.endpoint.contribution;

import com.sportygroup.jackpotsystem.contribution.domain.contribution.GetContributionsQuery;
import com.sportygroup.jackpotsystem.contribution.domain.contribution.GetContributionQuery;
import com.sportygroup.jackpotsystem.contribution.domain.contribution.DeleteContributionsCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Contributions")
public class ContributionController {

    private final GetContributionsQuery getContributionsQuery;
    private final GetContributionQuery getContributionQuery;
    private final DeleteContributionsCommand deleteContributionsCommand;

    @GetMapping("/internal/v1/contributions/jackpot/{jackpotId}")
    @Operation(summary = "[Internal] Get contributions by jackpot id")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GetContributionsResponse> getContributionsByJackpotId(@PathVariable("jackpotId") UUID jackpotId) {
        final var output = getContributionsQuery.execute(new GetContributionsQuery.Input(jackpotId));
        final var response = GetContributionsResponse.of(output);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/internal/v1/contributions/bet/{betId}")
    @Operation(summary = "[Internal] Get contribution by bet id")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GetContributionResponse> getContributionByBetId(@PathVariable("betId") UUID betId) {
        final var output = getContributionQuery.execute(new GetContributionQuery.Input(betId));
        final var response = GetContributionResponse.of(output);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/internal/v1/contributions/jackpot/{jackpotId}")
    @Operation(summary = "[Internal] Delete all contributions by jackpot id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteContributionsByJackpotId(@PathVariable("jackpotId") UUID jackpotId) {
        deleteContributionsCommand.execute(new DeleteContributionsCommand.Input(jackpotId));
        return ResponseEntity.noContent().build();
    }
}

