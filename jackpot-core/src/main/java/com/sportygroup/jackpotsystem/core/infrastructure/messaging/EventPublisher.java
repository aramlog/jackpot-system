package com.sportygroup.jackpotsystem.core.infrastructure.messaging;

public interface EventPublisher<T> {
    void publish(String topic, T event);
}