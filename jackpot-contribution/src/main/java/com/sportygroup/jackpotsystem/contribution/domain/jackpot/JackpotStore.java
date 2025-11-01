package com.sportygroup.jackpotsystem.contribution.domain.jackpot;

import java.util.Optional;
import java.util.UUID;

public interface JackpotStore {

    Jackpot save(Jackpot jackpot);

    Optional<Jackpot> findById(UUID id);
}

