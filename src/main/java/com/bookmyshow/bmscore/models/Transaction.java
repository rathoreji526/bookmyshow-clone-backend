package com.bookmyshow.bmscore.models;

import com.bookmyshow.bmscore.enums.TransactionStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "transactions")
public class Transaction extends GlobalFields{
    private UUID userId;
    private Double amount;
    private LocalDateTime expiry;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    private boolean processed = false;
}
