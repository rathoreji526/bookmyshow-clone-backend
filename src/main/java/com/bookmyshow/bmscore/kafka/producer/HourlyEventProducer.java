package com.bookmyshow.bmscore.kafka.producer;

import com.bookmyshow.bmscore.kafka.events.HourlyEvent;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class HourlyEventProducer {
    @Autowired
    KafkaTemplate<String, HourlyEvent> kafkaTemplate;

    @Transactional
    public void sendHourlyEvent(){
        HourlyEvent event = new HourlyEvent();
        event.setStatus("UP");
        kafkaTemplate.send("hourly-event", event);
    }
}
