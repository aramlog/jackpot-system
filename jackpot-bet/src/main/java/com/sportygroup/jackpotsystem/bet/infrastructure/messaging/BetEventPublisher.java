package com.sportygroup.jackpotsystem.bet.infrastructure.messaging;

import com.sportygroup.jackpotsystem.bet.domain.Bet;
import com.sportygroup.jackpotsystem.bet.domain.BetEvent;
import com.sportygroup.jackpotsystem.core.infrastructure.messaging.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BetEventPublisher {

    private final EventPublisher<Object> eventPublisher;

    @Value("${jackpot.kafka.topics.bet:jackpot-bets}")
    private String betTopic;

    public void publish(Bet bet) {
        final var event = new BetEvent(
                bet.betId(),
                bet.userId(),
                bet.jackpotId(),
                bet.betAmount(),
                bet.createdAt()
        );
        eventPublisher.publish(betTopic, event);
    }

    public String getTopic() {
        return betTopic;
    }
}