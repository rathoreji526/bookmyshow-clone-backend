package com.bookmyshow.bmscore.service;

import com.bookmyshow.bmscore.models.Location;
import com.bookmyshow.bmscore.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    public Location save(Location location){
        return locationRepository.save(location);
    }
    public Optional<Location> findByZipcodeAreaAndStreet(String zipcode, String area, String street){
        return locationRepository.findByZipcodeAndAreaAndStreet(zipcode , area , street);
    }
}
