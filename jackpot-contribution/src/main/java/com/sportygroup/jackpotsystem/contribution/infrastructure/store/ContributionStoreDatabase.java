package com.sportygroup.jackpotsystem.contribution.infrastructure.store;

import com.sportygroup.jackpotsystem.contribution.domain.contribution.ContributionStore;
import com.sportygroup.jackpotsystem.contribution.domain.contribution.JackpotContribution;
import com.sportygroup.jackpotsystem.contribution.infrastructure.TransactionalContribution;
import com.sportygroup.jackpotsystem.contribution.infrastructure.TransactionalContributionReadOnly;
import com.sportygroup.jackpotsystem.contribution.infrastructure.store.mapper.ContributionStoreMapper;
import com.sportygroup.jackpotsystem.contribution.infrastructure.store.repository.ContributionRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
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
    public List<JackpotContribution> findByJackpotId(UUID jackpotId) {
        return repository.findByJackpotId(jackpotId).stream()
                .map(contributionStoreMapper::toDomain)
                .toList();
    }

    @Override
    @TransactionalContribution
    public void deleteByJackpotId(UUID jackpotId) {
        repository.deleteAll(repository.findByJackpotId(jackpotId));
    }
}


