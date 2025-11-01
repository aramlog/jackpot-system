package com.sportygroup.jackpotsystem.contribution.infrastructure.store.mapper;

import com.sportygroup.jackpotsystem.contribution.domain.jackpot.Jackpot;
import com.sportygroup.jackpotsystem.contribution.infrastructure.store.entity.JackpotEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JackpotStoreMapper {

    Jackpot toDomain(JackpotEntity entity);

    JackpotEntity toEntity(Jackpot jackpot);
}

