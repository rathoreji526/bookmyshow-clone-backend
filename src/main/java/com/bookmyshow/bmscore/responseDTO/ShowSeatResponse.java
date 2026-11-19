package com.bookmyshow.bmscore.responseDTO;

import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

public interface ShowSeatResponse {
    UUID getId();
    @Value("#{target.seat.seatNumber}")
    String getSeatNumber();
    @Value("#{target.seat.seatType.name()}")
    String getSeatType();
    @Value("#{target.seat.row.name}")
    String getRowName();
    @Value("#{target.seat.isActive}")
    boolean getActive();
    String getSeatStatus();
    Double getPrice();
}
