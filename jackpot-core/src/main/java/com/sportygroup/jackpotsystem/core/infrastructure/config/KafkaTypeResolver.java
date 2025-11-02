package com.sportygroup.jackpotsystem.core.infrastructure.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.Headers;

/**
 * Type resolver for Kafka message deserialization.
 * Determines the target type based on message data or headers.
 * 
 * Note: Topic names must match those defined in application.yaml (jackpot.kafka.topics.*)
 */
@Slf4j
public class KafkaTypeResolver {

    // Topic name from application.yaml: jackpot.kafka.topics.bet
    private static final String TOPIC_BETS = "jackpot-bets";

    public static JavaType resolveType(String topic, byte[] data, Headers headers) {
        log.debug("Resolving type for topic: {}", topic);

        // Exact topic matching for better accuracy
        if (TOPIC_BETS.equals(topic)) {
            log.debug("Resolved to BetEvent for topic: {}", topic);
            return TypeFactory.defaultInstance().constructType(
                    com.sportygroup.jackpotsystem.core.domain.messaging.BetEvent.class
            );
        }

        // Log warning for unrecognized topics
        log.warn("Unrecognized topic '{}' - unable to determine message type. Returning Object.class", topic);
        return TypeFactory.defaultInstance().constructType(Object.class);
    }
}