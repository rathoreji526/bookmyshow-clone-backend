package com.bookmyshow.bmscore.kafka.events;

import com.bookmyshow.bmscore.enums.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TransactionEvent {
    private UUID transactionId;
    private TransactionStatus transactionStatus;
}
