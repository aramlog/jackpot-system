package com.sportygroup.jackpotsystem.bet.infrastructure.endpoint;

import com.sportygroup.jackpotsystem.bet.domain.GetBetQuery;
import com.sportygroup.jackpotsystem.bet.domain.PlaceBetCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Bets")
public class BetController {

    private final PlaceBetCommand placeBetCommand;
    private final GetBetQuery getBetQuery;

    @PostMapping("/public/v1/bets")
    @Operation(summary = "Place a bet")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PlaceBetResponse> placeBet(@Valid @RequestBody PlaceBetRequest request) {
        final var input = request.toInput();
        final var output = placeBetCommand.execute(input);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PlaceBetResponse.of(output));
    }

    @GetMapping("/internal/v1/bets/{betId}")
    @Operation(summary = "[Internal] Get bet by id")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GetBetResponse> getBetById(@PathVariable("betId") UUID betId) {
        final var output = getBetQuery.execute(new GetBetQuery.Input(betId));
        return ResponseEntity.ok(GetBetResponse.of(output));
    }
}

