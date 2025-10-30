package com.sportygroup.jackpotsystem.reward.infrastructure.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "jackpot_reward")
@Getter
@Setter
public class RewardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "bet_id", nullable = false)
    private UUID betId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "jackpot_id", nullable = false)
    private UUID jackpotId;

    @Column(name = "reward_amount")
    private BigDecimal rewardAmount;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}


