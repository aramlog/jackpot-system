package com.sportygroup.jackpotsystem.contribution.infrastructure.store.mapper;

import com.sportygroup.jackpotsystem.contribution.domain.jackpot.Jackpot;
import com.sportygroup.jackpotsystem.contribution.infrastructure.store.entity.JackpotEntity;
import org.springframework.stereotype.Component;

@Component
public class JackpotStoreMapper {

    public JackpotEntity toEntity(Jackpot jackpot) {
        if (jackpot == null) {
            return null;
        }
        JackpotEntity entity = new JackpotEntity();
        entity.setId(jackpot.id());
        entity.setName(jackpot.name());
        entity.setContributionType(jackpot.contributionType());
        entity.setInitialPool(jackpot.initialPool());
        entity.setFixedPercentage(jackpot.fixedPercentage());
        entity.setVariableInitialPercentage(jackpot.variableInitialPercentage());
        entity.setVariableMinPercentage(jackpot.variableMinPercentage());
        entity.setVariableThreshold(jackpot.variableThreshold());
        return entity;
    }

    public Jackpot toDomain(JackpotEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Jackpot(
                entity.getId(),
                entity.getName(),
                entity.getContributionType(),
                entity.getInitialPool(),
                entity.getFixedPercentage(),
                entity.getVariableInitialPercentage(),
                entity.getVariableMinPercentage(),
                entity.getVariableThreshold()
        );
    }
}
