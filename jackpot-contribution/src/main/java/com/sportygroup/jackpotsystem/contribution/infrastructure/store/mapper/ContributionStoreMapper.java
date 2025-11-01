package com.sportygroup.jackpotsystem.contribution.infrastructure.store.mapper;

import com.sportygroup.jackpotsystem.contribution.domain.contribution.JackpotContribution;
import com.sportygroup.jackpotsystem.contribution.infrastructure.store.entity.ContributionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContributionStoreMapper {

    ContributionEntity toEntity(JackpotContribution contribution);

    JackpotContribution toDomain(ContributionEntity entity);
}


