package com.bookmyshow.bmscore.controller;

import com.bookmyshow.bmscore.requestDTO.RegisterTheaterRequestDTO;
import com.bookmyshow.bmscore.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apis/theater")
public class TheaterController {
    @Autowired
    private TheaterService theaterService;

    @PostMapping("/register-theater")
    public ResponseEntity<String> registerTheater(@RequestBody RegisterTheaterRequestDTO dto){
        try{
            theaterService.registerTheater(dto);
            return new ResponseEntity<>("Theater registered successfully", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
