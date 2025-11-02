package com.sportygroup.jackpotsystem.core.domain.messaging;

/**
 * Domain interface for publishing events to a message broker.
 *
 * @param <T> the type of event to publish
 */
public interface EventPublisher<T> {

    /**
     * Publishes an event to the specified topic.
     *
     * @param topic the topic to publish to
     * @param event the event to publish
     */
    void publish(String topic, T event);
}