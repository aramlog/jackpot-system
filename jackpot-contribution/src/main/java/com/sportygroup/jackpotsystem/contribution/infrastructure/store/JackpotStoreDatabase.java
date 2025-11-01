package com.sportygroup.jackpotsystem.contribution.infrastructure.store;

import com.sportygroup.jackpotsystem.contribution.domain.jackpot.Jackpot;
import com.sportygroup.jackpotsystem.contribution.domain.jackpot.JackpotStore;
import com.sportygroup.jackpotsystem.contribution.infrastructure.TransactionalContribution;
import com.sportygroup.jackpotsystem.contribution.infrastructure.TransactionalContributionReadOnly;
import com.sportygroup.jackpotsystem.contribution.infrastructure.store.mapper.JackpotStoreMapper;
import com.sportygroup.jackpotsystem.contribution.infrastructure.store.repository.JackpotRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class JackpotStoreDatabase implements JackpotStore {

    private final JackpotRepository repository;
    private final JackpotStoreMapper jackpotStoreMapper;

    @Override
    @TransactionalContribution
    public Jackpot save(Jackpot jackpot) {
        var saved = repository.save(jackpotStoreMapper.toEntity(jackpot));
        return jackpotStoreMapper.toDomain(saved);
    }

    @Override
    @TransactionalContributionReadOnly
    public Optional<Jackpot> findById(UUID id) {
        return repository.findById(id)
                .map(jackpotStoreMapper::toDomain);
    }
}

