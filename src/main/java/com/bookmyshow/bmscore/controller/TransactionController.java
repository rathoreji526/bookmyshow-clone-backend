package com.bookmyshow.bmscore.controller;

import com.bookmyshow.bmscore.requestDTO.MakePaymentDTO;
import com.bookmyshow.bmscore.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apis/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/make-payment")
    public ResponseEntity<String> makePayment(@RequestBody MakePaymentDTO dto){
        try{
           String message = transactionService.makePayment(dto);
           return new ResponseEntity<>(message , HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
