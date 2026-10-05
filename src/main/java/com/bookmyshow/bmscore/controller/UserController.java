package com.bookmyshow.bmscore.controller;

import com.bookmyshow.bmscore.customExceptions.ConfirmPasswordMismatchException;
import com.bookmyshow.bmscore.customExceptions.UserAlreadyExistsException;
import com.bookmyshow.bmscore.models.Booking;
import com.bookmyshow.bmscore.requestDTO.SaveUserRequestDTO;
import com.bookmyshow.bmscore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apis/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/saveUser")
    public ResponseEntity<String> saveUser(@RequestBody SaveUserRequestDTO dto){
        try{
            userService.saveUser(dto);
            return new ResponseEntity<>("User saved successfully." , HttpStatus.CREATED);
        }catch(ConfirmPasswordMismatchException |
               UserAlreadyExistsException e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getBookings")
    public  ResponseEntity<List<Booking>> getBookings(@AuthenticationPrincipal UserDetails userDetails){
        try{
            List<Booking> bookings = userService.getBookings(userDetails.getUsername());
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }
}
