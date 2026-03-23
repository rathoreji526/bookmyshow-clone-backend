package com.bookmyshow.bmscore.repository;

import com.bookmyshow.bmscore.enums.TransactionStatus;
import com.bookmyshow.bmscore.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByStatusAndExpiryAfter(TransactionStatus status, LocalDateTime exp);
    List<Transaction> findByProcessedFalse();
}
