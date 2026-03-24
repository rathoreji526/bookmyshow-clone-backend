package com.bookmyshow.bmscore.service;

import com.bookmyshow.bmscore.models.Theater;
import com.bookmyshow.bmscore.repository.TheaterRepository;
import com.bookmyshow.bmscore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TheaterService {
    @Autowired
    TheaterRepository theaterRepo;
    @Autowired
    UserRepository userRepo;

    public void registerTheater(Theater theater) {

    }
}
