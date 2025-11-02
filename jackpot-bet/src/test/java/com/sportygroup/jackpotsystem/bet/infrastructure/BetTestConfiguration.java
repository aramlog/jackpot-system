package com.sportygroup.jackpotsystem.bet.infrastructure;

import com.sportygroup.jackpotsystem.core.domain.messaging.EventPublisher;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * Test configuration for integration tests.
 * Provides mocked beans for external dependencies (Kafka).
 */
@TestConfiguration
public class BetTestConfiguration {

    /**
     * Provides a mocked EventPublisher to replace the real Kafka-based one.
     */
    @Bean
    @Primary
    public EventPublisher<Object> eventPublisher() {
        return Mockito.mock(EventPublisher.class);
    }
}
