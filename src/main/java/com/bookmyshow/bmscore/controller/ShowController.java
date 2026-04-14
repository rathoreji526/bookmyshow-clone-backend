package com.bookmyshow.bmscore.controller;

import com.bookmyshow.bmscore.models.Show;
import com.bookmyshow.bmscore.requestDTO.CreateShowRequestDTO;
import com.bookmyshow.bmscore.requestDTO.FindShowDTO;
import com.bookmyshow.bmscore.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/apis/show")
public class ShowController {
    @Autowired
    private ShowService showService;

    @PostMapping("/create-show")
    public ResponseEntity<String> createShow(@RequestBody CreateShowRequestDTO dto){
        try{
            UUID showId = showService.createShow(dto);
            return new ResponseEntity<>("Show created successfully.\nShow id: "+showId, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find-show")
    public ResponseEntity<?> findShow(@RequestBody FindShowDTO dto){
        try{
            List<Show> shows = showService.findShow(dto);
            return new ResponseEntity<>(shows, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }
}
