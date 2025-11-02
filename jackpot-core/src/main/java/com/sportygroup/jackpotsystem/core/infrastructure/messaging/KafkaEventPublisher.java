package com.sportygroup.jackpotsystem.core.infrastructure.messaging;

import com.sportygroup.jackpotsystem.core.domain.messaging.EventPublisher;
import com.sportygroup.jackpotsystem.core.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventPublisher implements EventPublisher<Object> {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publish(String topic, Object event) {
        try {
            kafkaTemplate.send(topic, event);
            log.info("Published event to topic '{}': {}", topic, event);
        } catch (Exception e) {
            log.error("Failed to publish event to topic '{}': {}", topic, event, e);
            throw new InternalServerException("Failed to publish event to Kafka", e);
        }
    }
}

