package com.bookmyshow.bmscore.controller;

import com.bookmyshow.bmscore.models.Show;
import com.bookmyshow.bmscore.models.ShowSeat;
import com.bookmyshow.bmscore.requestDTO.SeatLockingRequestDTO;
import com.bookmyshow.bmscore.service.ShowSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/apis/seat")
public class ShowSeatController {
    @Autowired
    private ShowSeatService showSeatService;

    @PostMapping("/lock-seat")
    public ResponseEntity<String> lockSeat(@RequestBody SeatLockingRequestDTO dto){
        try{
            showSeatService.lockSeat(dto);
            return new ResponseEntity<>("" ,  HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllShowSeats")
    public ResponseEntity<List<ShowSeat>> getAllShowSeats(@RequestParam UUID showId){
        try{
            List<ShowSeat> showSeats = showSeatService.findAllByShowId(showId);
            return new  ResponseEntity<>(showSeats, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
