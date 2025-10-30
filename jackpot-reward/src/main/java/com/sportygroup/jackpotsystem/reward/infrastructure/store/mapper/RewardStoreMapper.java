package com.sportygroup.jackpotsystem.reward.infrastructure.store.mapper;

import com.sportygroup.jackpotsystem.reward.domain.JackpotReward;
import com.sportygroup.jackpotsystem.reward.infrastructure.store.entity.RewardEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RewardStoreMapper {

    RewardEntity toEntity(JackpotReward reward);

    JackpotReward toDomain(RewardEntity entity);
}


