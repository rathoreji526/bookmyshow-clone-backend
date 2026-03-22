package com.bookmyshow.bmscore.service;

import com.bookmyshow.bmscore.enums.SeatStatus;
import com.bookmyshow.bmscore.models.ShowSeat;
import com.bookmyshow.bmscore.repository.BookingRepository;
import com.bookmyshow.bmscore.repository.ShowSeatRepository;
import com.bookmyshow.bmscore.requestDTO.BookingRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepo;
    @Autowired
    ShowSeatRepository showSeatRepo;

    public void bookTicket(BookingRequestDTO dto){
        List<ShowSeat> showSeatList = showSeatRepo.findByLockedByAndSeatStatus(dto.getUserId() , SeatStatus.LOCKED);
        for(ShowSeat ss : dto.getSeats()){
//           if(ss.getLockedBy()!=)
        }
        /*
        checks before booking ticket
        purpose:
        1. if status of showSeat is not locked - reject.
        2. if showSeat is not locked by the user - reject.
        3. if locked time+5min is less than current time - reject

        after all checks
        create transaction with status pending
        it is dummy transaction so we will do only one thing we will make transaction status to completed/failed
        in every 10 seconds we will check the status of the transaction if it is completed make all the showSeats booked.

        */
    }
}
