package com.bookmyshow.bmscore.kafka.consumer;

import com.bookmyshow.bmscore.kafka.events.HourlyEvent;
import com.bookmyshow.bmscore.kafka.events.TransactionEvent;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HourlyEventConsumer {

    @Transactional
    @KafkaListener(topics = "hourly-event")
    @Retryable(maxAttempts = 3)
    public void processHourlyLogging(HourlyEvent event) {
        log.info("Received Hourly Event and status is: "+ event.getStatus());
    }
}
