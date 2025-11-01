package com.sportygroup.jackpotsystem.bet.infrastructure.endpoint;

import com.sportygroup.jackpotsystem.bet.domain.PlaceBetCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Bets")
public class BetController {

    private final PlaceBetCommand placeBetCommand;

    @PostMapping("/public/v1/bets")
    @Operation(summary = "Place a bet")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PlaceBetResponse> placeBet(@Valid @RequestBody PlaceBetRequest request) {
        final var input = request.toInput();
        final var output = placeBetCommand.execute(input);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PlaceBetResponse.of(output));
    }
}

