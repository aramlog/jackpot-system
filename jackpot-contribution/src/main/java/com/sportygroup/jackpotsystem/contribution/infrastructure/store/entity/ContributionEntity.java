package com.sportygroup.jackpotsystem.contribution.infrastructure.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "jackpot_contribution")
@Getter
@Setter
public class ContributionEntity {
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

    @Column(name = "stake_amount")
    private BigDecimal stakeAmount;

    @Column(name = "contribution_amount")
    private BigDecimal contributionAmount;

    @Column(name = "current_jackpot_amount")
    private BigDecimal currentJackpotAmount;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}


