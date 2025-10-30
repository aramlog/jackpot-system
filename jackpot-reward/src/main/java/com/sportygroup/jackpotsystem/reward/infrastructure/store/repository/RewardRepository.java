package com.sportygroup.jackpotsystem.reward.infrastructure.store.repository;

import com.sportygroup.jackpotsystem.reward.infrastructure.store.entity.RewardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface RewardRepository extends JpaRepository<RewardEntity, UUID> {
    List<RewardEntity> findByJackpotId(UUID jackpotId);

    List<RewardEntity> findByBetId(UUID betId);

    List<RewardEntity> findByCreatedAtBetween(Instant from, Instant to);
}