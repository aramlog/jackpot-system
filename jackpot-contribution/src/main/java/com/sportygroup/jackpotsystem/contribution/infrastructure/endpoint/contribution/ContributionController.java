package com.sportygroup.jackpotsystem.contribution.infrastructure.endpoint.contribution;

import com.sportygroup.jackpotsystem.contribution.domain.contribution.GetContributionsQuery;
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

    @GetMapping("/internal/v1/contributions/jackpot/{jackpotId}")
    @Operation(summary = "[Internal] Get contributions by jackpot id")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GetContributionsResponse> getContributionsByJackpotId(@PathVariable("jackpotId") UUID jackpotId) {
        final var output = getContributionsQuery.execute(new GetContributionsQuery.Input(jackpotId));
        final var response = GetContributionsResponse.of(output);
        return ResponseEntity.ok(response);
    }
}

