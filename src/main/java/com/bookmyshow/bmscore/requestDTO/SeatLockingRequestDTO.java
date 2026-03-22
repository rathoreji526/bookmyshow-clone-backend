package com.bookmyshow.bmscore.requestDTO;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class SeatLockingRequestDTO {
    private UUID userID;
    private List<UUID> seatIDs;
    private UUID showID;
}
