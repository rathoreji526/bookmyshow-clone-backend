package com.bookmyshow.bmscore.service;

import com.bookmyshow.bmscore.kafka.producer.HourlyEventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProduceHourlyEvent {
    @Autowired
    private HourlyEventProducer hourlyEventProducer;

    @Scheduled(fixedRate = 1000*60*60) //1hr
    public void produceEvent(){
        hourlyEventProducer.sendHourlyEvent();
        log.info("Produced Hourly Event");
    }
}
