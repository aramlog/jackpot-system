package com.sportygroup.jackpotsystem.reward.infrastructure.store;

import com.sportygroup.jackpotsystem.reward.domain.JackpotReward;
import com.sportygroup.jackpotsystem.reward.domain.RewardStore;
import com.sportygroup.jackpotsystem.reward.infrastructure.TransactionalReward;
import com.sportygroup.jackpotsystem.reward.infrastructure.TransactionalRewardReadOnly;
import com.sportygroup.jackpotsystem.reward.infrastructure.store.mapper.RewardStoreMapper;
import com.sportygroup.jackpotsystem.reward.infrastructure.store.repository.RewardRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class RewardStoreDatabase implements RewardStore {

    private final RewardRepository repository;
    private final RewardStoreMapper rewardStoreMapper;

    @Override
    @TransactionalReward
    public JackpotReward save(JackpotReward reward) {
        var saved = repository.save(rewardStoreMapper.toEntity(reward));
        return rewardStoreMapper.toDomain(saved);
    }

    @Override
    @TransactionalRewardReadOnly
    public Optional<JackpotReward> findById(UUID id) {
        return repository.findById(id).map(rewardStoreMapper::toDomain);
    }

    @Override
    @TransactionalRewardReadOnly
    public List<JackpotReward> findByJackpotId(UUID jackpotId) {
        return repository.findByJackpotId(jackpotId).stream()
                .map(rewardStoreMapper::toDomain)
                .toList();
    }

    @Override
    @TransactionalRewardReadOnly
    public List<JackpotReward> findByBetId(UUID betId) {
        return repository.findByBetId(betId).stream()
                .map(rewardStoreMapper::toDomain)
                .toList();
    }

    @Override
    @TransactionalRewardReadOnly
    public List<JackpotReward> findBetween(Instant from, Instant to) {
        return repository.findByCreatedAtBetween(from, to).stream()
                .map(rewardStoreMapper::toDomain)
                .toList();
    }
}


