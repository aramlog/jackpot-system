package com.sportygroup.jackpotsystem.bet.infrastructure.store.repository;

import com.sportygroup.jackpotsystem.bet.infrastructure.store.entity.BetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface BetRepository extends JpaRepository<BetEntity, UUID> {
    List<BetEntity> findByUserId(UUID userId);

    List<BetEntity> findByJackpotId(UUID jackpotId);

    List<BetEntity> findByCreatedAtBetween(Instant from, Instant to);
}


