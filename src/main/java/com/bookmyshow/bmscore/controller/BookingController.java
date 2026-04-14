package com.bookmyshow.bmscore.controller;

import com.bookmyshow.bmscore.models.Booking;
import com.bookmyshow.bmscore.requestDTO.InitiatePaymentRequestDTO;
import com.bookmyshow.bmscore.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/apis/booking")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping("/initiate-payment")
    public ResponseEntity<String> initiatePayment(@RequestBody InitiatePaymentRequestDTO dto){
        try{
            UUID transactionId = bookingService.initiatePayment(dto);
            return new ResponseEntity<>("Complete your payment to confirm booking.\nTransaction id: "+transactionId, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find-bookings")
    public ResponseEntity<List<Booking>> findBookings(@RequestParam UUID userId){
        try{
            List<Booking> bokingList = bookingService.findAllBookings(userId);
            return new ResponseEntity<>(bokingList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
