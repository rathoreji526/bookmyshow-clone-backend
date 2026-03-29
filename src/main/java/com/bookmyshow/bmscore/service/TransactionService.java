package com.bookmyshow.bmscore.service;

import com.bookmyshow.bmscore.customExceptions.TransactionAlreadyProcessedException;
import com.bookmyshow.bmscore.enums.TransactionStatus;
import com.bookmyshow.bmscore.models.Transaction;
import com.bookmyshow.bmscore.repository.TransactionRepository;
import com.bookmyshow.bmscore.requestDTO.MakePaymentDTO;
import jakarta.transaction.InvalidTransactionException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepo;
    @Autowired
    BookingService bookingService;

    public String makePayment(MakePaymentDTO dto) throws InvalidTransactionException {
        Transaction transaction = transactionRepo.findById(dto.getTransactionId())
                .orElseThrow(() -> new InvalidTransactionException("Invalid transaction!"));

        if(!transaction.getStatus().equals(TransactionStatus.PENDING)) {
            throw new TransactionAlreadyProcessedException("Transaction is already processed.");
        }

        if(dto.isTransactionCompleted()) {
            transaction.setStatus(TransactionStatus.COMPLETED);
            transactionRepo.save(transaction);
            return "Transaction is completed.\nCheckout email or booking section for tickets.";
        }
        else {
            transaction.setStatus(TransactionStatus.CANCELED);
            transactionRepo.save(transaction);
            return "Transaction is cancelled.";
        }
    }

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
