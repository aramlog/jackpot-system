package com.sportygroup.jackpotsystem.bet.infrastructure.store;

import com.sportygroup.jackpotsystem.bet.domain.Bet;
import com.sportygroup.jackpotsystem.bet.domain.BetStore;
import com.sportygroup.jackpotsystem.bet.infrastructure.store.mapper.BetStoreMapper;
import com.sportygroup.jackpotsystem.bet.infrastructure.store.repository.BetRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class BetStoreDatabase implements BetStore {

    private final BetRepository repository;
    private final BetStoreMapper betStoreMapper;

    @Override
    @TransactionalBet
    public Bet save(Bet bet) {
        var entity = betStoreMapper.toEntity(bet);
        var saved = repository.save(entity);
        return betStoreMapper.toDomain(saved);
    }

    @Override
    @TransactionalBetReadOnly
    public Optional<Bet> findById(UUID betId) {
        return repository.findById(betId)
                .map(betStoreMapper::toDomain);
    }
}


