package com.bookmyshow.bmscore.requestDTO;

import com.bookmyshow.bmscore.enums.SeatType;
import lombok.Getter;

@Getter
public class SeatLayoutDTO {
    private String rowName;
    private int startSeat;
    private int endSeat;
    private SeatType seatType;
}
