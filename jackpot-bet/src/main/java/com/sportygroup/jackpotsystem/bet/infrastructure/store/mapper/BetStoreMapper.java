package com.sportygroup.jackpotsystem.bet.infrastructure.store.mapper;

import com.sportygroup.jackpotsystem.bet.domain.Bet;
import com.sportygroup.jackpotsystem.bet.infrastructure.store.entity.BetEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BetStoreMapper {

    BetEntity toEntity(Bet bet);

    Bet toDomain(BetEntity entity);
}


