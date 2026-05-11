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
    private ScreenService screenService;
    @Autowired
    private ShowRepository showRepo;
    @Autowired
    private ShowSeatService showSeatService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private TheaterService theaterService;
    @Autowired
    private CommonUtilities utilities;

    @Transactional
    public UUID createShow(CreateShowRequestDTO dto){
        Theater theater = theaterService.findById(dto.getTheaterId());
        Movie movie = movieService.findById(dto.getMovieId());
        Screen screen = screenService.findById(dto.getScreenId());
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
        showSeatService.saveAll(showSeats);
        return show.getId();
    }

    public List<Show> findShow(FindShowDTO dto){
        if(!movieService.existsById(dto.getMovieId())){
            throw new MovieNotFoundException("Movie with id: "+dto.getMovieId()+" not found.");
        }
        return showRepo.findByMovieIdAndStartTimeAfter(dto.getMovieId() , dto.getDate());
    }

    public Show findById(UUID id){
        return showRepo.findById(id)
                .orElseThrow(()->new InvalidShowIdException("Show with id: "+id+" not found."));
    }
    public boolean existsById(UUID id){
        return showRepo.existsById(id);
    }

//    @Scheduled(fixedRate = 1000*60*5)
    public void updateShowStatus(){
        int modified = showRepo.updateShowStatus(LocalDateTime.now());
        log.info("{} shows updated", modified);
    }
}
