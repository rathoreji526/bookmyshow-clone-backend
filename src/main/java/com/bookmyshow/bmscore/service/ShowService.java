package com.bookmyshow.bmscore.service;

import com.bookmyshow.bmscore.customExceptions.*;
import com.bookmyshow.bmscore.enums.SeatStatus;
import com.bookmyshow.bmscore.enums.SeatType;
import com.bookmyshow.bmscore.enums.ShowStatus;
import com.bookmyshow.bmscore.models.*;
import com.bookmyshow.bmscore.repository.*;
import com.bookmyshow.bmscore.requestDTO.CreateShowRequestDTO;
import com.bookmyshow.bmscore.requestDTO.FindShowDTO;
import com.bookmyshow.bmscore.utilities.CommonUtilities;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ShowService {
    @Autowired
    private ScreenRepository screenRepo;
    @Autowired
    private ShowRepository showRepo;
    @Autowired
    private ShowSeatRepository showSeatRepo;
    @Autowired
    private MovieRepository movieRepo;
    @Autowired
    private TheaterRepository theaterRepo;
    @Autowired
    private CommonUtilities utilities;

    @Transactional
    public UUID createShow(CreateShowRequestDTO dto){
        Theater theater = theaterRepo.findById(dto.getTheaterId())
                .orElseThrow(()->new TheaterNotExistsException("Theater not found"));
        Movie movie = movieRepo.findById(dto.getMovieId())
                .orElseThrow(()->new MovieNotFoundException("Movie with id: "+dto.getMovieId()+" not found."));
        Screen screen = screenRepo.findById(dto.getScreenId())
                .orElseThrow(()->new ScreenNotFoundException("Screen with id: "+dto.getScreenId()+" not found."));
        List<Show> overlappingShows = showRepo.findOverlappingShows(dto.getScreenId(), dto.getStartTime().minusMinutes(15), dto.getEndTime().plusMinutes(15));

        if(!overlappingShows.isEmpty()){
            StringBuilder error = new StringBuilder("Shows are overlapping can not create show at this time!\n");
            for(Show show : overlappingShows){
                String message = "Start time:" + show.getStartTime()+"\nEnd time:" + show.getEndTime()+"\n";
                error.append(message);
            }
            throw new OverlappingShowsException(error.toString());
        }

        if(!screen.isActive()){
            throw new InactiveScreenException("Provided screen is not active.");
        }
        /// show creation
        Show show = new Show();
        show.setMovie(movie);
        show.setLanguage(dto.getLanguage());
        show.setFormat(dto.getFormat());
        show.setStartTime(dto.getStartTime());
        show.setEndTime(dto.getEndTime());
        show.setShowStatus(ShowStatus.UPCOMING);
        show.setScreen(screen);
        String id = utilities.generateShowSysId();
        show.setSysId(id);
        showRepo.save(show);

        /// seat mapping
        List<ShowSeat>  showSeats = new ArrayList<>();
        log.info("rows size: "+ screen.getRows().size());
        for (Row row : screen.getRows()) {
            for(Seat seat : row.getSeats()) {
                if(!seat.isActive())continue;

                ShowSeat ss = new ShowSeat();
                ss.setSeatStatus(SeatStatus.AVAILABLE);
                ss.setShow(show);
                ss.setSeat(seat);
                //set price of showSeat
                if(seat.getSeatType().equals(SeatType.PREMIUM))ss.setPrice(dto.getPrice_premium());
                else if(seat.getSeatType().equals(SeatType.GOLD))ss.setPrice(dto.getPrice_gold());
                else ss.setPrice(dto.getPrice_silver());

                showSeats.add(ss);
            }
        }
        log.info(showSeats.size()+" ");
        showSeatRepo.saveAll(showSeats);
        return show.getId();
    }

    public List<Show> findShow(FindShowDTO dto){
        if(movieRepo.findById(dto.getMovieId()).isEmpty()){
            throw new MovieNotFoundException("Movie with id: "+dto.getMovieId()+" not found.");
        }
        return showRepo.findByMovieIdAndStartTimeAfter(dto.getMovieId() , dto.getDate());
    }

    @Scheduled(fixedRate = 1000*60*5)
    public void updateShowStatus(){
        int modified = showRepo.updateShowStatus(LocalDateTime.now());
        log.info("{} shows updated", modified);
    }
}
