package com.bookmyshow.bmscore.service;

import com.bookmyshow.bmscore.customExceptions.InvalidShowIdException;
import com.bookmyshow.bmscore.customExceptions.SeatAlreadyBookedOrLockedException;
import com.bookmyshow.bmscore.models.ShowSeat;
import com.bookmyshow.bmscore.repository.ShowSeatRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ShowSeatService {
    @Autowired
    private ShowSeatRepository showSeatRepo;

    @Transactional
    public void lockSeat(List<UUID> seatIds , UUID userId , UUID showId){
        int lockedSeats = showSeatRepo.lockSeatsBulk(
                seatIds,
                userId,
                showId,
                LocalDateTime.now().plusMinutes(5),
                LocalDateTime.now());

        if(lockedSeats != seatIds.size()) {
            log.info("Only "+lockedSeats+" seats are locked");
            throw new SeatAlreadyBookedOrLockedException("Some seats already booked/locked!");
        }

    }
    public List<ShowSeat> findAllByShowId(UUID showId) {
        if(!showSeatRepo.existsByShowId(showId)) {
            throw new InvalidShowIdException("Wrong show id!");
        }
        return showSeatRepo.findByShowId(showId);
    }

    public List<ShowSeat> findByIdIn(List<UUID> showSeatIds) {
        return showSeatRepo.findByIdIn(showSeatIds);
    }
    public void saveAll(List<ShowSeat> showSeats) {
        showSeatRepo.saveAll(showSeats);
    }

//    @Scheduled(fixedDelay = 1000*60*1)
    public void autoReleaseSeats(){
        int releasedSeats = showSeatRepo.releaseLockedSeat(LocalDateTime.now());
        log.info("Released "+releasedSeats+" seats.");
    }
}
