package com.bookmyshow.bmscore.requestDTO;

import com.bookmyshow.bmscore.models.ShowSeat;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BookingRequestDTO {
    private UUID userId;
    private List<ShowSeat> seats;

}
