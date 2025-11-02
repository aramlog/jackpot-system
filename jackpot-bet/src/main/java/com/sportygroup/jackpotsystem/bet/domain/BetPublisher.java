package com.sportygroup.jackpotsystem.bet.domain;

/**
 * Domain interface for publishing bet events.
 */
public interface BetPublisher {

    /**
     * Publishes a bet event.
     *
     * @param bet the bet to publish as an event
     */
    void publish(Bet bet);

    /**
     * Gets the topic where bet events are published.
     *
     * @return the topic name
     */
    String getTopic();
}

