package com.sportygroup.jackpotsystem.core.infrastructure.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventPublisher implements EventPublisher<Object> {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publish(String topic, Object event) {
        try {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, event);
            log.info("Published event to topic '{}': {}", topic, event);
        } catch (Exception e) {
            log.error("Failed to publish event to topic '{}': {}", topic, event, e);
            throw new RuntimeException("Failed to publish event to Kafka", e);
        }
    }
}

