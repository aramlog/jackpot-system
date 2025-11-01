package com.sportygroup.jackpotsystem.contribution.infrastructure.endpoint.jackpot;

import com.sportygroup.jackpotsystem.contribution.domain.jackpot.CreateJackpotCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.util.UUID;

@Value
public class CreateJackpotResponse {

    @Schema(
            description = "Created jackpot identifier",
            example = "550e8400-e29b-41d4-a716-446655440000")
    UUID jackpotId;

    @Schema(
            description = "Jackpot name",
            example = "Fixed Percentage Jackpot")
    String name;

    public static CreateJackpotResponse of(CreateJackpotCommand.Output output) {
        return new CreateJackpotResponse(
                output.jackpotId(),
                output.name()
        );
    }
}

