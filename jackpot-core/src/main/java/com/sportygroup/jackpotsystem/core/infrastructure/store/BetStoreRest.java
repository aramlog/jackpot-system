package com.sportygroup.jackpotsystem.core.infrastructure.store;

import com.sportygroup.jackpotsystem.core.domain.store.BetStore;
import com.sportygroup.jackpotsystem.core.exception.ServiceUnavailableException;
import com.sportygroup.jackpotsystem.core.infrastructure.store.client.BetFeignClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class BetStoreRest implements BetStore {

    private final BetFeignClient betFeignClient;

    @Override
    public Optional<BetResult> findById(UUID betId) {
        try {
            final var response = betFeignClient.getBetById(betId);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new ServiceUnavailableException("Bet retrieval failed with status: " + response.getStatusCode());
            }
            final var betResponse = response.getBody();
            if (betResponse == null) {
                return Optional.empty();
            }
            return Optional.of(new BetResult(
                    betResponse.betId(),
                    betResponse.userId(),
                    betResponse.jackpotId(),
                    betResponse.betAmount(),
                    betResponse.createdAt()
            ));
        } catch (FeignException.NotFound ex) {
            return Optional.empty();
        } catch (FeignException ex) {
            throw new ServiceUnavailableException("Failed to get bet by id", ex);
        }
    }
}

