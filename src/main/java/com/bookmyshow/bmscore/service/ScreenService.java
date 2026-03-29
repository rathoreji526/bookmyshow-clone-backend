package com.bookmyshow.bmscore.service;

import com.bookmyshow.bmscore.customExceptions.ScreenNameAlreadyExistsException;
import com.bookmyshow.bmscore.customExceptions.SeatAlreadyExistsException;
import com.bookmyshow.bmscore.customExceptions.TheaterNotExistsException;
import com.bookmyshow.bmscore.models.Row;
import com.bookmyshow.bmscore.models.Screen;
import com.bookmyshow.bmscore.models.Seat;
import com.bookmyshow.bmscore.models.Theater;
import com.bookmyshow.bmscore.repository.ScreenRepository;
import com.bookmyshow.bmscore.repository.TheaterRepository;
import com.bookmyshow.bmscore.requestDTO.CreateScreenDTO;
import com.bookmyshow.bmscore.requestDTO.SeatLayoutDTO;
import com.bookmyshow.bmscore.utilities.CommonUtilities;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ScreenService {
    @Autowired
    private ScreenRepository screenRepo;
    @Autowired
    CommonUtilities utilities;
    @Autowired
    private TheaterRepository theaterRepo;

    @Transactional
    public void createScreen(CreateScreenDTO dto) {

        /// if screen is already exists
        if(screenRepo.findByNameAndTheaterId(utilities.normalizeString(dto.getScreenName()), dto.getTheaterId()).isPresent()) {
            throw new ScreenNameAlreadyExistsException("Screen name already exists!");
        }

        ///
        Theater theater = theaterRepo.findById(dto.getTheaterId())
                .orElseThrow(()->new TheaterNotExistsException("Theater does not exists."));

        Map<String , Row> rowMap = new TreeMap<>();
        Set<String>  seatSet = new HashSet<>();


        for(SeatLayoutDTO layout : dto.getLayout()){

            rowMap.putIfAbsent(utilities.normalizeString(layout.getRowName()) ,  new Row());
            Row row = rowMap.get(utilities.normalizeString(layout.getRowName()));

            if(row.getName()==null){
                row.setName(utilities.normalizeString(layout.getRowName()));
                row.setSeats(new ArrayList<>());
            }

            if(layout.getStartSeat() > layout.getEndSeat()){
                throw new IllegalArgumentException("Invalid seat range.");
            }

            for(int i = layout.getStartSeat() ; i <= layout.getEndSeat() ; i++){
                String seatName = utilities.normalizeString(layout.getRowName()+i);

                if(seatSet.contains(seatName)){
                    throw new SeatAlreadyExistsException("Duplicate Seat: "+seatName);
                }
                seatSet.add(seatName);

                Seat seat = new Seat();
                seat.setSeatNumber(seatName);
                seat.setRow(row);
                seat.setSeatType(layout.getSeatType());
                row.getSeats().add(seat);
            }
            row.getSeats().sort(Comparator.comparingInt(seat ->
                    Integer.parseInt(seat.getSeatNumber().replaceAll("[^0-9]", ""))
            ));

        }
        Screen screen = new Screen();
        screen.setName(utilities.normalizeString(dto.getScreenName()));
        screen.setRows(new ArrayList<>(rowMap.values()));
        screen.setTheater(theater);
        screenRepo.save(screen);
    }
}
