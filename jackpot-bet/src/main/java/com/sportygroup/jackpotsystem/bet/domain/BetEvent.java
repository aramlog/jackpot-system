package com.sportygroup.jackpotsystem.bet.domain;

import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Value
public class BetEvent {
    UUID betId;
    UUID userId;
    UUID jackpotId;
    BigDecimal betAmount;
    Instant createdAt;
}

