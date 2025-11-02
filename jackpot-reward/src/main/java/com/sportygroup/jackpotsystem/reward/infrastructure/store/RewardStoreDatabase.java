package com.sportygroup.jackpotsystem.reward.infrastructure.store;

import com.sportygroup.jackpotsystem.reward.domain.JackpotReward;
import com.sportygroup.jackpotsystem.reward.domain.RewardStore;
import com.sportygroup.jackpotsystem.reward.infrastructure.TransactionalReward;
import com.sportygroup.jackpotsystem.reward.infrastructure.store.mapper.RewardStoreMapper;
import com.sportygroup.jackpotsystem.reward.infrastructure.store.repository.RewardRepository;
import lombok.RequiredArgsConstructor;

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
}


