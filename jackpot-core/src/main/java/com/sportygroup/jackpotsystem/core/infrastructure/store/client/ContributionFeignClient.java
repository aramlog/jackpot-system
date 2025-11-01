package com.sportygroup.jackpotsystem.core.infrastructure.store.client;

import com.sportygroup.jackpotsystem.core.infrastructure.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@FeignClient(
        value = "jackpot-contribution",
        url = "${jackpot.contributionApiBaseUrl:}",
        configuration = FeignClientConfig.class
)
public interface ContributionFeignClient {

    @GetMapping(value = "/internal/v1/contributions/jackpot/{jackpotId}", produces = "application/json")
    ResponseEntity<ContributionResponse> getContributionsByJackpotId(@PathVariable("jackpotId") UUID jackpotId);

    record ContributionResponse(List<ContributionItem> contributions) {
    }

    record ContributionItem(
            UUID id,
            UUID betId,
            UUID userId,
            UUID jackpotId,
            BigDecimal stakeAmount,
            BigDecimal contributionAmount,
            BigDecimal currentJackpotAmount,
            Instant createdAt
    ) {
    }
}

