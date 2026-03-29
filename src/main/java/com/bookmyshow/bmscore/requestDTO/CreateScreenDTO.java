package com.bookmyshow.bmscore.requestDTO;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class CreateScreenDTO {
    private UUID theaterId;
    private String screenName;
    private int totalRows;
    private int seatsPerRow;
    private List<SeatLayoutDTO> layout;
}