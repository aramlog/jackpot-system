package com.sportygroup.jackpotsystem.core.infrastructure.store.client;

import com.sportygroup.jackpotsystem.core.infrastructure.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@FeignClient(
        value = "jackpot-bet",
        url = "${jackpot.betApiBaseUrl:}",
        configuration = FeignClientConfig.class
)
public interface BetFeignClient {

    @GetMapping(value = "/internal/v1/bets/{betId}", produces = "application/json")
    ResponseEntity<BetResponse> getBetById(@PathVariable("betId") UUID betId);

    record BetResponse(
            UUID betId,
            UUID userId,
            UUID jackpotId,
            BigDecimal betAmount,
            Instant createdAt
    ) {
    }
}

