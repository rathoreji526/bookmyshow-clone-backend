package com.bookmyshow.bmscore.controller;

import com.bookmyshow.bmscore.models.Screen;
import com.bookmyshow.bmscore.requestDTO.CreateScreenDTO;
import com.bookmyshow.bmscore.service.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/apis/screen")
public class ScreenController {
    @Autowired
    private ScreenService screenService;

    @PostMapping("/add-screen")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<String> addScreen(@RequestBody CreateScreenDTO dto){
        try{
            screenService.createScreen(dto);
            return new ResponseEntity<>("Screen addedd successfully", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/findBytheaterId")
    public List<Screen> findByTheaterId(@RequestParam UUID theaterID){
        return screenService.findByTheaterId(theaterID);
    }
}
