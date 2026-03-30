package com.bookmyshow.bmscore.service;

import com.bookmyshow.bmscore.customExceptions.InvalidShowIdException;
import com.bookmyshow.bmscore.customExceptions.SeatAlreadyBookedOrLockedException;
import com.bookmyshow.bmscore.models.ShowSeat;
import com.bookmyshow.bmscore.repository.ShowRepository;
import com.bookmyshow.bmscore.repository.ShowSeatRepository;
import com.bookmyshow.bmscore.requestDTO.SeatLockingRequestDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ShowSeatService {
    @Autowired
    private ShowSeatRepository showSeatRepo;
    @Autowired
    private ShowRepository showRepo;

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
    public List<ShowSeat> findAllByShowId(UUID showId) {
        if(showRepo.findById(showId).isEmpty()){
            throw new InvalidShowIdException("Wrong show id!");
        }
        return showSeatRepo.findByShowId(showId);
    }
}
