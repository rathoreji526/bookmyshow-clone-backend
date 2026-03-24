package com.bookmyshow.bmscore.utilities;

import com.bookmyshow.bmscore.repository.ShowRepository;
import com.bookmyshow.bmscore.repository.TheaterRepository;
import com.bookmyshow.bmscore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommonUtilities {
    @Autowired
    UserRepository userRepo;
    @Autowired
    ShowRepository showRepo;
    @Autowired
    TheaterRepository theaterRepo;
    BCryptPasswordEncoder passwordEncoder = new  BCryptPasswordEncoder();

    public String hashPassword(String password){
        return passwordEncoder.encode(password);
    }

    public boolean matchPassword(String password, String hashedPassword){
        return passwordEncoder.matches(hashedPassword , password);
    }

    public String generateUserSysId(){
        String sysId = "USER-"+ UUID.randomUUID().toString().substring(0 , 6).toUpperCase();
        while(userRepo.existsBySysId(sysId)){
            sysId = "USER-"+ UUID.randomUUID().toString().substring(0 , 6).toUpperCase();
        }
        return sysId;
    }
    public String generateShowSysId(){
        String sysId = "SH-"+ UUID.randomUUID().toString().substring(0 , 6).toUpperCase();
        while(showRepo.existsBySysId(sysId)){
            sysId = "SH-"+ UUID.randomUUID().toString().substring(0 , 6).toUpperCase();
        }
        return sysId;
    }
    public String generateTheaterSysId(){
        String sysId = ("TH-"+ UUID.randomUUID().toString().substring(0 , 6)).toUpperCase();
        while(theaterRepo.existsBySysId(sysId)){
            sysId = ("TH-"+ UUID.randomUUID().toString().substring(0 , 6)).toUpperCase();
        }
        return sysId;
    }
    public String generateLocationSysId(){
        String sysId = ("LOC-"+ UUID.randomUUID().toString().substring(0 , 6)).toUpperCase();
        while(theaterRepo.existsBySysId(sysId)){
            sysId = ("LOC-"+ UUID.randomUUID().toString().substring(0 , 6)).toUpperCase();
        }
        return sysId;
    }

}
