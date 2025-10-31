package com.sportygroup.jackpotsystem.bet.infrastructure.endpoint;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
public class PlaceBetRequest {

    @NotNull
    @Schema(
            description = "User identifier",
            example = "550e8400-e29b-41d4-a716-446655440001")
    UUID userId;

    @NotNull
    @Schema(
            description = "Jackpot identifier",
            example = "550e8400-e29b-41d4-a716-446655440002")
    UUID jackpotId;

    @NotNull
    @DecimalMin("0.0")
    @Schema(description = "Bet amount", example = "100.00")
    BigDecimal betAmount;
}
