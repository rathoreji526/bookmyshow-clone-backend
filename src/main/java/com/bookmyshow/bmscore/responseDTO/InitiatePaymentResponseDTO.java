package com.bookmyshow.bmscore.responseDTO;

import lombok.Data;

import java.util.UUID;

@Data
public class InitiatePaymentResponseDTO {
    String message;
    UUID txnId;
}
