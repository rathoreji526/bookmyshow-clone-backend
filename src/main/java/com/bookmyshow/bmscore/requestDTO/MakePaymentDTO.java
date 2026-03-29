package com.bookmyshow.bmscore.requestDTO;

import lombok.Getter;

import java.util.UUID;

@Getter
public class MakePaymentDTO {
    private UUID transactionId;
    private boolean transactionCompleted;
}
