package com.bookmyshow.bmscore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/health")
@RestController
public class Health {

    @GetMapping
    public String health(){
        return "OK";
    }
}
