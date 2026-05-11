package com.bookmyshow.bmscore.kafka.producer;

import com.bookmyshow.bmscore.enums.TransactionStatus;
import com.bookmyshow.bmscore.kafka.events.TransactionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TransactionEventProducer{
    @Autowired
    private KafkaTemplate<String, TransactionEvent>  kafkaTemplate;

    @Transactional
    public void sendTransactionEvent(UUID txnId , TransactionStatus transactionStatus){
        TransactionEvent event = new TransactionEvent();
        event.setTransactionId(txnId);
        event.setTransactionStatus(transactionStatus);
        kafkaTemplate.send("transactions.events" , txnId.toString() , event);
    }
}
