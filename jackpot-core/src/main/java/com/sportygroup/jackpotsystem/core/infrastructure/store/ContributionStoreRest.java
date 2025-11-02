package com.sportygroup.jackpotsystem.core.infrastructure.store;

import com.sportygroup.jackpotsystem.core.domain.store.ContributionStore;
import com.sportygroup.jackpotsystem.core.exception.NotFoundException;
import com.sportygroup.jackpotsystem.core.exception.ServiceUnavailableException;
import com.sportygroup.jackpotsystem.core.infrastructure.store.client.ContributionFeignClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ContributionStoreRest implements ContributionStore {

    private final ContributionFeignClient contributionFeignClient;

    @Override
    public List<JackpotContributionResult> getContributionsByJackpotId(UUID jackpotId) {
        try {
            final ResponseEntity<ContributionFeignClient.ContributionResponse> response =
                    contributionFeignClient.getContributionsByJackpotId(jackpotId);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new ServiceUnavailableException("Contribution retrieval failed with status: " + response.getStatusCode());
            }
            return Objects.requireNonNull(response.getBody()).contributions().stream()
                    .map(item -> new JackpotContributionResult(
                            item.id(),
                            item.betId(),
                            item.userId(),
                            item.jackpotId(),
                            item.stakeAmount(),
                            item.contributionAmount(),
                            item.currentJackpotAmount(),
                            item.createdAt()
                    ))
                    .collect(Collectors.toList());
        } catch (FeignException ex) {
            throw new ServiceUnavailableException("Failed to get contributions by jackpot id", ex);
        }
    }

    @Override
    public JackpotContributionResult getContributionByBetId(UUID betId) {
        try {
            final ResponseEntity<ContributionFeignClient.ContributionItem> response =
                    contributionFeignClient.getContributionByBetId(betId);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new ServiceUnavailableException("Contribution retrieval failed with status: " + response.getStatusCode());
            }
            final var item = Objects.requireNonNull(response.getBody());
            return new JackpotContributionResult(
                    item.id(),
                    item.betId(),
                    item.userId(),
                    item.jackpotId(),
                    item.stakeAmount(),
                    item.contributionAmount(),
                    item.currentJackpotAmount(),
                    item.createdAt()
            );
        } catch (FeignException.NotFound ex) {
            throw new NotFoundException("Contribution not found for betId: " + betId);
        } catch (FeignException ex) {
            throw new ServiceUnavailableException("Failed to get contribution by bet id", ex);
        }
    }

    @Override
    public void deleteContributionsByJackpotId(UUID jackpotId) {
        try {
            contributionFeignClient.deleteContributionsByJackpotId(jackpotId);
        } catch (FeignException ex) {
            throw new ServiceUnavailableException("Failed to delete contributions by jackpot id", ex);
        }
    }
}

