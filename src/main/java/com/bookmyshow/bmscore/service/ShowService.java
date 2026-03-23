package com.bookmyshow.bmscore.service;

import com.bookmyshow.bmscore.customExceptions.MovieNotFoundException;
import com.bookmyshow.bmscore.customExceptions.ScreenNotFoundException;
import com.bookmyshow.bmscore.enums.SeatStatus;
import com.bookmyshow.bmscore.enums.SeatType;
import com.bookmyshow.bmscore.enums.ShowStatus;
import com.bookmyshow.bmscore.models.*;
import com.bookmyshow.bmscore.repository.*;
import com.bookmyshow.bmscore.requestDTO.CreateShowRequestDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Transactional
    public void createShow(CreateShowRequestDTO dto){
        Movie movie = movieRepo.findBySysId(dto.getMovieId())
                .orElseThrow(()->new MovieNotFoundException("Movie with id: "+dto.getMovieId()+" not found."));
        Screen screen = screenRepo.findBySysId(dto.getScreenId())
                .orElseThrow(()->new ScreenNotFoundException("Screen with id: "+dto.getScreenId()+" not found."));

        /// show creation
        Show show = new Show();
        show.setMovie(movie);
        show.setLanguage(dto.getLanguage());
        show.setFormat(dto.getFormat());
        show.setStartTime(dto.getStartTime());
        show.setEndTime(dto.getEndTime());
        show.setShowStatus(ShowStatus.UPCOMING);
        show.setScreen(screen);
        String id = "SH-"+ UUID.randomUUID().toString().substring(0 , 6);
        show.setSysId(id);
        showRepo.save(show);

        /// seat mapping
        List<ShowSeat>  showSeats = new ArrayList<>();
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
        showSeatRepo.saveAll(showSeats);
    }
}
