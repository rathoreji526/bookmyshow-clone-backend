package com.bookmyshow.bmscore.service;

import com.bookmyshow.bmscore.customExceptions.OwnerNotRegisteredException;
import com.bookmyshow.bmscore.customExceptions.TheaterAlreadyExistsException;
import com.bookmyshow.bmscore.enums.Role;
import com.bookmyshow.bmscore.models.Location;
import com.bookmyshow.bmscore.models.Theater;
import com.bookmyshow.bmscore.models.User;
import com.bookmyshow.bmscore.repository.LocationRepository;
import com.bookmyshow.bmscore.repository.TheaterRepository;
import com.bookmyshow.bmscore.repository.UserRepository;
import com.bookmyshow.bmscore.requestDTO.LocationRequestDTO;
import com.bookmyshow.bmscore.requestDTO.RegisterTheaterRequestDTO;
import com.bookmyshow.bmscore.utilities.CommonUtilities;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class TheaterService {
    @Autowired
    TheaterRepository theaterRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    CommonUtilities utilities;
    @Autowired
    LocationRepository locationRepo;

    @Transactional
    public void registerTheater(RegisterTheaterRequestDTO dto) {
        LocationRequestDTO locationDto = dto.getLocation();
        Optional<Location> loc = locationRepo.findByZipcodeAndAreaAndStreet(utilities.normalizeString(locationDto.getZipcode()), utilities.normalizeString(locationDto.getArea()), utilities.normalizeString(locationDto.getStreet()));
        Location location = new Location();
        if (loc.isPresent()) {
            Optional<Theater> theater = theaterRepo.findByNameAndLocation_Id(utilities.normalizeString(dto.getTheaterName()) , loc.get().getId());
            if(theater.isPresent()){
                throw new TheaterAlreadyExistsException("Theater is already registered!");
            }
            location = loc.get();
        }else{
            location.setCountry(utilities.normalizeString(locationDto.getCountry()));
            location.setCity(utilities.normalizeString(locationDto.getCity()));
            location.setStreet(utilities.normalizeString(locationDto.getStreet()));
            location.setState(utilities.normalizeString(locationDto.getState()));
            location.setZipcode(utilities.normalizeString(locationDto.getZipcode()));
            location = locationRepo.save(location);
        }

        User user = userRepo.findById(dto.getOwnerId())
                .orElseThrow(()->new OwnerNotRegisteredException("Owner is not registered!"));

        if(user.getRole() != Role.OWNER){
            user.setRole(Role.OWNER);
            userRepo.save(user);
        }

        Theater theater = new Theater();
        theater.setName(utilities.normalizeString(dto.getTheaterName()));
        theater.setGstNumber(dto.getGstNumber());
        theater.setPanNumber(dto.getPanNumber());
        theater.setBusinessLicenseNumber(dto.getBusinessLicenseNumber());
        log.info(user.toString());
        theater.setOwner(user);
        theater.setLocation(location);

        if(theaterRepo.findByNameAndLocation_Id(utilities.normalizeString(dto.getTheaterName()), location.getId()).isPresent()){
            throw  new TheaterAlreadyExistsException("Theater already exists.");
        }
        theaterRepo.save(theater);
    }
}
