package com.sportygroup.jackpotsystem.contribution.infrastructure.store.repository;

import com.sportygroup.jackpotsystem.contribution.infrastructure.store.entity.ContributionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface ContributionRepository extends JpaRepository<ContributionEntity, UUID> {
    List<ContributionEntity> findByJackpotId(UUID jackpotId);

    List<ContributionEntity> findByBetId(UUID betId);

    List<ContributionEntity> findByCreatedAtBetween(Instant from, Instant to);
}


