package com.sportygroup.jackpotsystem.contribution.infrastructure.endpoint.jackpot;

import com.sportygroup.jackpotsystem.contribution.domain.jackpot.CreateJackpotCommand;
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
@Tag(name = "Jackpots")
public class JackpotController {

    private final CreateJackpotCommand createJackpotCommand;

    @PostMapping("/protected/v1/jackpots")
    @Operation(summary = "Create a new jackpot with contribution configuration")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CreateJackpotResponse> createJackpot(@Valid @RequestBody CreateJackpotRequest request) {
        final var input = request.toInput();
        final var output = createJackpotCommand.execute(input);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CreateJackpotResponse.of(output));
    }
}

