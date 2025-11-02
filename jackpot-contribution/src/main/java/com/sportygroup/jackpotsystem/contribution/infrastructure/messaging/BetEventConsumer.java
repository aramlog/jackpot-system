package com.sportygroup.jackpotsystem.contribution.infrastructure.messaging;

import com.sportygroup.jackpotsystem.contribution.domain.ProcessBetEventCommand;
import com.sportygroup.jackpotsystem.core.domain.messaging.BetEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BetEventConsumer {

    private final ProcessBetEventCommand processBetEventCommand;

    @KafkaListener(topics = "${jackpot.kafka.topics.bet:jackpot-bets}", groupId = "jackpot-contribution")
    public void consumeBetEvent(@Payload BetEvent betEvent,
                                @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                                @Header(KafkaHeaders.OFFSET) long offset) {
        try {
            log.info("Received bet event from topic '{}', partition: {}, offset: {}", topic, partition, offset);
            log.info("Deserialized BetEvent: betId={}, userId={}, jackpotId={}, amount={}",
                    betEvent.betId(), betEvent.userId(), betEvent.jackpotId(), betEvent.betAmount());

            processBetEventCommand.execute(new ProcessBetEventCommand.Input(betEvent));

            log.info("Bet event processed successfully");
        } catch (Exception e) {
            log.error("Error processing bet event from topic '{}': {}", topic, betEvent, e);
            throw e;
        }
    }
}

