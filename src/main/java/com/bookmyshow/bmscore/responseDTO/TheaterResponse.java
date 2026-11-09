package com.bookmyshow.bmscore.responseDTO;

import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

public interface TheaterResponse {
    UUID getId();
    String getName();
    @Value("#{target.location.city}")
    String getCity();
    @Value("#{target.location.state}")
    String getState();
    @Value("#{target.location.zipcode}")
    String getZipcode();
}
