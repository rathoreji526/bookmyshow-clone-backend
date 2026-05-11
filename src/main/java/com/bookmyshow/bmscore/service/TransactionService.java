package com.bookmyshow.bmscore.service;

import com.bookmyshow.bmscore.customExceptions.TransactionAlreadyProcessedException;
import com.bookmyshow.bmscore.enums.TransactionStatus;
import com.bookmyshow.bmscore.kafka.producer.TransactionEventProducer;
import com.bookmyshow.bmscore.models.Transaction;
import com.bookmyshow.bmscore.repository.TransactionRepository;
import com.bookmyshow.bmscore.requestDTO.MakePaymentDTO;
import jakarta.transaction.InvalidTransactionException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepo;
    @Autowired
    private TransactionEventProducer txnEventProducer;

    @Transactional
    public String makePayment(MakePaymentDTO dto) throws InvalidTransactionException {
        Transaction transaction = transactionRepo.findById(dto.getTransactionId())
                .orElseThrow(() -> new InvalidTransactionException("Invalid transaction!"));

        if(!transaction.getStatus().equals(TransactionStatus.PENDING)) {
            throw new TransactionAlreadyProcessedException("Transaction is already processed.");
        }

        if(dto.isTransactionCompleted()) {
            transaction.setStatus(TransactionStatus.COMPLETED);
            transactionRepo.save(transaction);
            //publish to kafka
            txnEventProducer.sendTransactionEvent(dto.getTransactionId() , TransactionStatus.COMPLETED);

            return "Transaction is completed. Processing your booking...";
        }
        else {
            transaction.setStatus(TransactionStatus.CANCELED);
            transactionRepo.save(transaction);

            //publish to kafka
            txnEventProducer.sendTransactionEvent(transaction.getId() ,  TransactionStatus.CANCELED);

            return "Transaction is cancelled.";
        }
    }
    public void save(Transaction transaction){
        transactionRepo.save(transaction);
    }
    public Transaction createAndSave(UUID userId , Double bookingValue){
        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        transaction.setAmount(bookingValue);
        transaction.setExpiry(LocalDateTime.now().plusMinutes(5));
        return transactionRepo.save(transaction);
    }

//    @Scheduled(fixedDelay = 10000*60*15)
    public void checkPendingTransactions(){
        List<Transaction> pendingTransactionsList = transactionRepo.findByProcessedFalse();

        log.info("running scheduler");
        for(Transaction transaction : pendingTransactionsList){
            if(transaction.getStatus().equals(TransactionStatus.COMPLETED)){
                log.info(transaction.toString());
                log.info("\ncalling from check_pending_transactions schedular method.\n");
                txnEventProducer.sendTransactionEvent(transaction.getId() , TransactionStatus.COMPLETED);
                log.info(transaction.toString());
                transaction.setProcessed(true);
            }
            else if(transaction.getStatus().equals(TransactionStatus.CANCELED)){
                txnEventProducer.sendTransactionEvent(transaction.getId() ,  TransactionStatus.CANCELED);
                transaction.setProcessed(true);
            }
            if(transaction.isProcessed()){
                transactionRepo.save(transaction);
            }
        }
    }
}
