package com.sportygroup.jackpotsystem.bet.infrastructure.endpoint;

import com.sportygroup.jackpotsystem.bet.domain.Bet;
import com.sportygroup.jackpotsystem.bet.domain.GetBetQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Value
public class GetBetResponse {

    @Schema(
            description = "Bet identifier",
            example = "550e8400-e29b-41d4-a716-446655440000")
    UUID betId;

    @Schema(
            description = "User identifier",
            example = "550e8400-e29b-41d4-a716-446655440001")
    UUID userId;

    @Schema(
            description = "Jackpot identifier",
            example = "550e8400-e29b-41d4-a716-446655440000")
    UUID jackpotId;

    @Schema(
            description = "Bet amount",
            example = "100.00")
    BigDecimal betAmount;

    @Schema(
            description = "Bet creation timestamp",
            example = "2025-11-01T12:00:00Z")
    Instant createdAt;

    public static GetBetResponse of(GetBetQuery.Output output) {
        final Optional<Bet> bet = output.bet();
        if (bet.isEmpty()) {
            throw new IllegalArgumentException("Bet not found");
        }
        final Bet b = bet.get();
        return new GetBetResponse(
                b.betId(),
                b.userId(),
                b.jackpotId(),
                b.betAmount(),
                b.createdAt()
        );
    }
}

