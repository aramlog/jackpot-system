package com.sportygroup.jackpotsystem.bet.infrastructure.endpoint;

import com.sportygroup.jackpotsystem.bet.domain.PlaceBetCommand;
import lombok.Value;

import java.util.UUID;

@Value
public class PlaceBetResponse {

    UUID betId;
    String topic;

    public static PlaceBetResponse of(PlaceBetCommand.Output output) {
        return new PlaceBetResponse(
                output.betId(),
                output.topic()
        );
    }
}

