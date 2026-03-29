package com.bookmyshow.bmscore.controller;

import com.bookmyshow.bmscore.requestDTO.InitiatePaymentRequestDTO;
import com.bookmyshow.bmscore.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apis/booking")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping("/initiate-payment")
    public ResponseEntity<String> initiatePayment(@RequestBody InitiatePaymentRequestDTO dto){
        try{
            bookingService.initiatePayment(dto);
            return new ResponseEntity<>("Complete your payment to confirm booking.", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
