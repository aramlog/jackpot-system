package com.sportygroup.jackpotsystem.contribution.infrastructure.store.repository;

import com.sportygroup.jackpotsystem.contribution.infrastructure.store.entity.JackpotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JackpotRepository extends JpaRepository<JackpotEntity, UUID> {
}

