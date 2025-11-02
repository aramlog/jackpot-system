package com.sportygroup.jackpotsystem.reward.infrastructure.store.mapper;

import com.sportygroup.jackpotsystem.reward.domain.JackpotReward;
import com.sportygroup.jackpotsystem.reward.infrastructure.store.entity.RewardEntity;
import org.springframework.stereotype.Component;

@Component
public class RewardStoreMapper {

    public RewardEntity toEntity(JackpotReward reward) {
        if (reward == null) {
            return null;
        }
        RewardEntity entity = new RewardEntity();
        entity.setId(reward.id());
        entity.setBetId(reward.betId());
        entity.setUserId(reward.userId());
        entity.setJackpotId(reward.jackpotId());
        entity.setRewardAmount(reward.rewardAmount());
        entity.setCreatedAt(reward.createdAt());
        return entity;
    }

    public JackpotReward toDomain(RewardEntity entity) {
        if (entity == null) {
            return null;
        }
        return new JackpotReward(
                entity.getId(),
                entity.getBetId(),
                entity.getUserId(),
                entity.getJackpotId(),
                entity.getRewardAmount(),
                entity.getCreatedAt()
        );
    }
}
