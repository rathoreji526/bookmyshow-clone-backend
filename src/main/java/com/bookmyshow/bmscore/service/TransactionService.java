package com.bookmyshow.bmscore.service;

import com.bookmyshow.bmscore.enums.TransactionStatus;
import com.bookmyshow.bmscore.models.Transaction;
import com.bookmyshow.bmscore.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepo;
    @Autowired
    BookingService bookingService;

//    @Scheduled(fixedDelay = 10000)
    public void checkPendingTransactions(){
        List<Transaction> pendingTransactionsList = transactionRepo.findByProcessedFalse();

        log.info("running scheduler");
        for(Transaction transaction : pendingTransactionsList){
            if(transaction.getStatus().equals(TransactionStatus.COMPLETED)){
                bookingService.handleConfirmedTransaction(transaction.getId());
                transaction.setProcessed(true);
            }
            else if(transaction.getStatus().equals(TransactionStatus.CANCELED)){
                bookingService.handleCancelledTransaction(transaction.getId());
                transaction.setProcessed(true);
            }
            if(transaction.isProcessed()){
                transactionRepo.save(transaction);
            }
        }
    }
}
