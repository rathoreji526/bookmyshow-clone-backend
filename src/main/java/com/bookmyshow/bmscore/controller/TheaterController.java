package com.bookmyshow.bmscore.controller;

import com.bookmyshow.bmscore.models.Theater;
import com.bookmyshow.bmscore.requestDTO.RegisterTheaterRequestDTO;
import com.bookmyshow.bmscore.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apis/theater")
public class TheaterController {
    @Autowired
    private TheaterService theaterService;

    @PostMapping("/register-theater")
    public ResponseEntity<String> registerTheater(
            @RequestBody RegisterTheaterRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        try{
            theaterService.registerTheater(dto, userDetails);
            return new ResponseEntity<>("Theater registered successfully.", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getAll")
    public List<Theater> getAllTheaters(){
        return theaterService.findAllTheaters();
    }

    @GetMapping("/getAllTheaterByAdmin")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<?> getAllTheatersByAdmin(@AuthenticationPrincipal UserDetails userDetails){
        try{
            List<Theater> theaterList = theaterService.findByOwnerId(userDetails.getUsername());
            return new ResponseEntity<>(theaterList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("/getAllTheaterByCity/{city}")
    public ResponseEntity<?> getAllTheatersByCity(@PathVariable String city){
        try {
            List<Theater> theaterList = theaterService.findTheatersByCity(city);
            return new  ResponseEntity<>(theaterList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
