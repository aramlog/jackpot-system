package com.sportygroup.jackpotsystem.contribution.infrastructure.store.mapper;

import com.sportygroup.jackpotsystem.contribution.domain.contribution.JackpotContribution;
import com.sportygroup.jackpotsystem.contribution.infrastructure.store.entity.ContributionEntity;
import org.springframework.stereotype.Component;

@Component
public class ContributionStoreMapper {

    public ContributionEntity toEntity(JackpotContribution contribution) {
        if (contribution == null) {
            return null;
        }
        ContributionEntity entity = new ContributionEntity();
        entity.setId(contribution.id());
        entity.setBetId(contribution.betId());
        entity.setUserId(contribution.userId());
        entity.setJackpotId(contribution.jackpotId());
        entity.setStakeAmount(contribution.stakeAmount());
        entity.setContributionAmount(contribution.contributionAmount());
        entity.setCurrentJackpotAmount(contribution.currentJackpotAmount());
        entity.setCreatedAt(contribution.createdAt());
        return entity;
    }

    public JackpotContribution toDomain(ContributionEntity entity) {
        if (entity == null) {
            return null;
        }
        return new JackpotContribution(
                entity.getId(),
                entity.getBetId(),
                entity.getUserId(),
                entity.getJackpotId(),
                entity.getStakeAmount(),
                entity.getContributionAmount(),
                entity.getCurrentJackpotAmount(),
                entity.getCreatedAt()
        );
    }
}
