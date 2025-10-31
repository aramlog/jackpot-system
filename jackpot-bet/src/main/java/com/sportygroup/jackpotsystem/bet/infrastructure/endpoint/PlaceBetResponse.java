package com.sportygroup.jackpotsystem.bet.infrastructure.endpoint;

import lombok.Value;

import java.util.UUID;

@Value
public class PlaceBetResponse {

    UUID betId;
    String topic;
}

