package com.bookmyshow.bmscore.requestDTO;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class InitiatePaymentRequestDTO {
    private UUID userId;
    private List<UUID> showSeatIds;

}
