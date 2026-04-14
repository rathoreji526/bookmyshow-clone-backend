package com.bookmyshow.bmscore.service;

import com.bookmyshow.bmscore.customExceptions.InvalidShowIdException;
import com.bookmyshow.bmscore.customExceptions.SeatAlreadyBookedOrLockedException;
import com.bookmyshow.bmscore.models.ShowSeat;
import com.bookmyshow.bmscore.repository.ShowRepository;
import com.bookmyshow.bmscore.repository.ShowSeatRepository;
import com.bookmyshow.bmscore.requestDTO.SeatLockingRequestDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ShowSeatService {
    @Autowired
    private ShowSeatRepository showSeatRepo;
    @Autowired
    private ShowRepository showRepo;

    @Transactional
    public void lockSeat(SeatLockingRequestDTO dto){
        int lockedSeats = showSeatRepo.lockSeatsBulk(
                dto.getSeatIDs(),
                dto.getUserID(),
                dto.getShowID(),
                LocalDateTime.now().plusMinutes(5),
                LocalDateTime.now());

        if(lockedSeats != dto.getSeatIDs().size()) {
            log.info("Only "+lockedSeats+" seats are locked");
            throw new SeatAlreadyBookedOrLockedException("Some seats already booked/locked!");
        }

    }
    public List<ShowSeat> findAllByShowId(UUID showId) {
        if(showRepo.findById(showId).isEmpty()){
            throw new InvalidShowIdException("Wrong show id!");
        }
        return showSeatRepo.findByShowId(showId);
    }

    @Scheduled(fixedDelay = 1000*60*1)
    public void autoReleaseSeats(){
        int releasedSeats = showSeatRepo.releaseLockedSeat(LocalDateTime.now());
        log.info("Released "+releasedSeats+" seats.");
    }
}
