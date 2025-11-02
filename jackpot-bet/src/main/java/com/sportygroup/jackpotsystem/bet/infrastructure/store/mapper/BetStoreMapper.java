package com.sportygroup.jackpotsystem.bet.infrastructure.store.mapper;

import com.sportygroup.jackpotsystem.bet.domain.Bet;
import com.sportygroup.jackpotsystem.bet.infrastructure.store.entity.BetEntity;
import org.springframework.stereotype.Component;

@Component
public class BetStoreMapper {

    public BetEntity toEntity(Bet bet) {
        if (bet == null) {
            return null;
        }
        BetEntity entity = new BetEntity();
        entity.setBetId(bet.betId());
        entity.setUserId(bet.userId());
        entity.setJackpotId(bet.jackpotId());
        entity.setBetAmount(bet.betAmount());
        entity.setCreatedAt(bet.createdAt());
        return entity;
    }

    public Bet toDomain(BetEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Bet(
                entity.getBetId(),
                entity.getUserId(),
                entity.getJackpotId(),
                entity.getBetAmount(),
                entity.getCreatedAt()
        );
    }
}
