package com.bookmyshow.bmscore.kafka.consumer;

import com.bookmyshow.bmscore.customExceptions.InvalidTransactionException;
import com.bookmyshow.bmscore.enums.TransactionStatus;
import com.bookmyshow.bmscore.kafka.events.TransactionEvent;
import com.bookmyshow.bmscore.models.Transaction;
import com.bookmyshow.bmscore.repository.TransactionRepository;
import com.bookmyshow.bmscore.service.BookingService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TransactionEventConsumer {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private TransactionRepository transactionRepo;

    @Transactional
    @KafkaListener(topics = "transactions.events")
    public void processTransactionEvent(TransactionEvent event) {
        Transaction transaction = transactionRepo.findById(event.getTransactionId())
                .orElseThrow(() -> new InvalidTransactionException("Transaction not found"));

        if(event.getTransactionStatus() == TransactionStatus.COMPLETED){
            bookingService.handleConfirmedTransaction(transaction.getId());
        }else{
            bookingService.handleCancelledTransaction(transaction.getId());
        }
        transaction.setProcessed(true);
        transactionRepo.save(transaction);
        log.info("Payment processed instantly via Kafka for transaction: {}", transaction.getId());
    }
}
