package com.sportygroup.jackpotsystem.bet.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

/**
 * Test application configuration for integration tests.
 * Mirrors the main Application class but avoids circular dependency issues.
 * Excludes Kafka auto-configuration since we're mocking EventPublisher.
 * Feign clients are mocked using @MockitoBean in the test class.
 */
@SpringBootApplication(
        scanBasePackages = "com.sportygroup.jackpotsystem",
        exclude = {KafkaAutoConfiguration.class}
)
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}

