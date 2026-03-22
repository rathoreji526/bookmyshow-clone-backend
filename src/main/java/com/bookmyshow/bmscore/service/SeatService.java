package com.bookmyshow.bmscore.service;

import com.bookmyshow.bmscore.customExceptions.SeatAlreadyBookedOrLockedException;
import com.bookmyshow.bmscore.repository.ShowSeatRepository;
import com.bookmyshow.bmscore.requestDTO.SeatLockingRequestDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SeatService {
    @Autowired
    ShowSeatRepository showSeatRepo;

    @Transactional
    public void lockSeat(SeatLockingRequestDTO dto){
        int bookedSeats = showSeatRepo.lockSeatsBulk(
                dto.getSeatIDs(),
                dto.getUserID(),
                LocalDateTime.now().plusMinutes(5),
                LocalDateTime.now());

        if(bookedSeats != dto.getSeatIDs().size()) {
            throw new SeatAlreadyBookedOrLockedException("Some seats already booked/locked");
        }

    }
}
/*

show seat saved
at the time of seat blocking,
these things are given: seat id , userId , showId

*/