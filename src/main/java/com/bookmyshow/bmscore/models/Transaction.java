package com.bookmyshow.bmscore.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction extends GlobalFields{
    private UUID userId;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
}
