package com.sportygroup.jackpotsystem.contribution.infrastructure.store.repository;

import com.sportygroup.jackpotsystem.contribution.infrastructure.store.entity.ContributionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContributionRepository extends JpaRepository<ContributionEntity, UUID> {

    List<ContributionEntity> findByJackpotIdOrderByCreatedAtAsc(UUID jackpotId);

    Optional<ContributionEntity> findByBetId(UUID betId);

}


