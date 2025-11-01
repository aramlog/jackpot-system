package com.sportygroup.jackpotsystem.contribution.infrastructure.store.entity;

import com.sportygroup.jackpotsystem.contribution.domain.ContributionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "jackpot")
@Getter
@Setter
public class JackpotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "contribution_type", nullable = false)
    private ContributionType contributionType;

    @Column(name = "initial_pool", nullable = false)
    private BigDecimal initialPool;

    @Column(name = "fixed_percentage")
    private BigDecimal fixedPercentage;

    @Column(name = "variable_initial_percentage")
    private BigDecimal variableInitialPercentage;

    @Column(name = "variable_min_percentage")
    private BigDecimal variableMinPercentage;

    @Column(name = "variable_threshold")
    private BigDecimal variableThreshold;
}

