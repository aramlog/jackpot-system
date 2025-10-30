package com.sportygroup.jackpotsystem.contribution.infrastructure.store;

import com.sportygroup.jackpotsystem.contribution.domain.ContributionStore;
import com.sportygroup.jackpotsystem.contribution.domain.JackpotContribution;
import com.sportygroup.jackpotsystem.contribution.infrastructure.TransactionalContribution;
import com.sportygroup.jackpotsystem.contribution.infrastructure.TransactionalContributionReadOnly;
import com.sportygroup.jackpotsystem.contribution.infrastructure.store.mapper.ContributionStoreMapper;
import com.sportygroup.jackpotsystem.contribution.infrastructure.store.repository.ContributionRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class ContributionStoreDatabase implements ContributionStore {

    private final ContributionRepository repository;
    private final ContributionStoreMapper contributionStoreMapper;

    @Override
    @TransactionalContribution
    public JackpotContribution save(JackpotContribution contribution) {
        var saved = repository.save(contributionStoreMapper.toEntity(contribution));
        return contributionStoreMapper.toDomain(saved);
    }

    @Override
    @TransactionalContributionReadOnly
    public Optional<JackpotContribution> findById(UUID id) {
        return repository.findById(id).map(contributionStoreMapper::toDomain);
    }

    @Override
    @TransactionalContributionReadOnly
    public List<JackpotContribution> findByJackpotId(UUID jackpotId) {
        return repository.findByJackpotId(jackpotId).stream()
                .map(contributionStoreMapper::toDomain)
                .toList();
    }

    @Override
    @TransactionalContributionReadOnly
    public List<JackpotContribution> findByBetId(UUID betId) {
        return repository.findByBetId(betId).stream()
                .map(contributionStoreMapper::toDomain)
                .toList();
    }

    @Override
    @TransactionalContributionReadOnly
    public List<JackpotContribution> findBetween(Instant from, Instant to) {
        return repository.findByCreatedAtBetween(from, to).stream()
                .map(contributionStoreMapper::toDomain)
                .toList();
    }
}


