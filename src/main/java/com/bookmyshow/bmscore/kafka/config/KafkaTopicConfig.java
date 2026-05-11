package com.bookmyshow.bmscore.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic transactionEventsTopic(){
        return TopicBuilder.name("transactions.events")
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic bookingConfirmTopic(){
        return TopicBuilder.name("ticket.email.events")
                .partitions(1)
                .replicas(1)
                .build();
    }
}